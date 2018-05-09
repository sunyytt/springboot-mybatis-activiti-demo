package com.example.demo.Junit;

import com.example.demo.DemoApplication;
import com.example.demo.model.WorkFlow;
import com.example.demo.service.workflow.WorkFlowService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class WorkFlowTest {
    private static Logger logger = LoggerFactory.getLogger(WorkFlowTest.class);
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    /**
     * 请假流程
     * 申请按钮：1.insert 关联表：记录业务id，processInstanceid，最新的taskID，下一个审批人，始终一条数据
     *         2.insert 日志表 ：记录申请理由，审批理由，时间，状态等，多条数据
     *         3.发布流程 ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TestProcess");
     *         4.查询任务 Task t = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
     *         5.提交申请： taskService.complete(taskId );
     * 审批人  ：1.查询代办任务：List<Task> tasks = taskService.createTaskQuery().taskAssignee(name).list();
     *          2.点击任务  查询关联表，显示业务记录
     *          3.完成同意/拒绝，点击提交按钮  更新关联表如下一个审批人，最新的taskID，insert 日志表
     *          4. 提交：taskService.complete(taskId );
     *
     * 获取所有节点 流程部署至服务器上之后可使用 可做进度条：
     * BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
     * 查询历史活动走过的节点：
     * List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
     *   .processInstanceId(processInstanceId).list();
     *
     */



    /**
     * 启动请假单流程  并获取流程实例
     * 因为该请假单流程可以会启动多个所以每启动一个请假单流程都会在数据库中插入一条新版本的流程数据
     * 通过key启动的流程就是当前key下最新版本的流程
     * 当流程发布后在  act_ru_task ，act_ru_execution， act_ru_identitylink 表中插入流程数据
     */
    @Test
    public void startFlow(){
        logger.info("======发布流程 =======");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TestProcess");
        logger.info("id:{} activitiId:{},DefinitionId:{}",processInstance.getId(),processInstance.getActivityId(),processInstance.getProcessDefinitionId());
        logger.info("======发布流程 =======");
        /**
         * insert 申请表时 （可以保存业务id,下一个审批人或者组，只有一条记录） 要保存 两个：processInstance.getId()；申请最后保存t.getId();以便获取最新的taskid
         * 设计一对多的结果表记录结果（记录同意或者拒绝）
         */
        //根据实例id查询当前的taskid
        Task t = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        t.getId();
    }

    @Test
    public void queryTask(){
        logger.info("======查看任务======");
        String processInstanceId="实例：processInstance.getId()";
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
            logger.info("ID:{}，姓名:{},接收人:{},开始时间",
                    task.getId(),
                    task.getName(),
                    task.getAssignee(),
                    task.getCreateTime());
        logger.info("======查看任务======");
    }


    @Test
    public void queryTasks(){
        logger.info("======查看任务======");
//        String name="张三";
        String name="老板";
        //根据接受人获取该用户的任务   代办任务
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(name).list();
        for (Task task : tasks) {
            logger.info("ID:{}，姓名:{},接收人:{},开始时间",
                    task.getId(),
                    task.getName(),
                    task.getAssignee(),
                    task.getCreateTime());
        }
        logger.info("======查看任务======");
    }


    //提出请假申请，启动流程
    @Test
    public void startTask(){
        logger.info("======提出请假申请，启动流程======");
        //taskId 就是查询任务中的 ID task.getId(),
        String taskId = "12511";
        //完成请假申请任务
        taskService.complete(taskId );
        logger.info("======提出请假申请，启动流程======");
    }


    //流程完毕，可以再act_hi_actinst表中看到整个请假流程
    @Test
    public void startTask2(){
        logger.info("======审批：完成请假申请任务======");
        //taskId 就是查询任务中的 ID task.getId(),
//        String taskId = "12511";
        String taskId = "20002";
        //完成请假申请任务
        taskService.complete(taskId );
        logger.info("======审批：完成请假申请任务======");
    }

    /**
     * 查询历史活动  act_hi_actinst
     * 见：https://blog.csdn.net/zjx86320/article/details/50363544
     */
    @Test
    public void findHisActivitiList(){
        String processInstanceId = "12508";//一个流程一个   PROC_INST_ID_
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if(list != null && list.size()>0){
            for(HistoricActivityInstance hai : list){
                System.out.println(hai.getId()+"  "+hai.getActivityName());
            }
        }
    }


    /**
     * 获取所有节点 流程部署至服务器上之后可使用 可做进度条
     */
    @Test
    public void getALL(){
        String processDefinitionId="TestProcess:2:10006";
        BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
        if(model != null) {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            for(FlowElement e : flowElements) {
                System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString());
            }
        }
    }

//    @Test
//    public void testStart(){
//        WorkFlow workFlow = new WorkFlow();
//        String id = UUID.randomUUID().toString();
//        workFlow.setId(id);
//        workFlow.setUserId("user5");
//        workFlow.setUserName("username5");
//        workFlow.setDays("5");
//        workFlow.setReason("年假");
//        workFlowService.start(workFlow);
//    }
//
//    @Test
//    public void testQueryFlowByUserId(){
//        List<WorkFlow> flows = new ArrayList<WorkFlow>();
//        String id = "698d6a24-9eb4-488c-94be-8422908cab9b";
//        flows= workFlowService.queryFlowByUserId(id);
//        System.out.println(flows.get(0).getDays());
//    }
//
//    @Test
//    public void update(){
//        WorkFlow workFlow = new WorkFlow();
//        workFlow.setId("389663db-af6f-410d-814e-803eb8703453");
//        workFlow.setDays("15");
//        workFlowService.updateById(workFlow);
//    }
}
