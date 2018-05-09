package com.example.demo.Junit;

import com.example.demo.DemoApplication;
import com.example.demo.model.WorkFlow;
import com.example.demo.service.workflow.WorkFlowService;
import org.activiti.engine.HistoryService;
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
        logger.info("id:{} activitiId:{}",processInstance.getId(),processInstance.getActivityId());
        logger.info("======发布流程 =======");
    }

    @Test
    public void queryTask(){
        logger.info("======查看任务======");
//        String name="张三";
        String name="老板";
        //根据接受人获取该用户的任务
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
//        String taskId = "12511";
        String taskId = "20002";
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
    //查询历史活动  act_hi_actinst
    //https://blog.csdn.net/zjx86320/article/details/50363544
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
