package com.example.demo.Junit.workflow;

import com.example.demo.DemoApplication;
import com.example.demo.dao.FlCorrelationMapper;
import com.example.demo.dao.FlLogsMapper;
import com.example.demo.model.FlCorrelation;
import com.example.demo.model.FlLogs;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class TaskListenerProcessTest {
    private static Logger logger = LoggerFactory.getLogger(TaskListenerProcessTest.class);

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FlCorrelationMapper flCorrelationMapper;
    @Autowired
    private FlLogsMapper flLogsMapper;

    /***
     * 参加：https://blog.csdn.net/lishaoran369/article/details/70236317
     *     https://www.cnblogs.com/ANCAN-RAY/articles/6646631.html
     * 测试使用实现TaskListener的方法动态传参
     * 一个流程只有一个processInstanceId，变量：taskid（当前只有一个，在整个流程中不断变化）
     * act_run_identitylink 这张表会把能审批的人都显示出来
     */

    @Test
    public void apply(){
        //web参数
        FlLogs flLogs = new FlLogs();
        flLogs.setUserId("applyID");
        flLogs.setUserName("郭襄");
        flLogs.setReason("休假3天");
        flLogs.setStatus("1");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TestTaskListenerProcess");
        logger.info("======查看当前所有节点======");
        String processDefinitionId=processInstance.getProcessDefinitionId();
        BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
        if(model != null) {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            for(FlowElement e : flowElements) {
                System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString());
            }
        }
        logger.info("======查看当前所有节点======");
        //根据assignee
        logger.info("======查看["+flLogs.getUserName()+"]个人任务======");

        logger.info("======查看当前["+flLogs.getUserName()+"]个人任务======");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        logger.info("ID:{}，姓名:{},接收人:{},开始时间：{},processInstanceId:{}",
                task.getId(),
                task.getName(),
                task.getAssignee(),
                task.getCreateTime(),
                processInstance.getId());
        logger.info("======查看当前["+flLogs.getUserName()+"]个人任务======");

        logger.info("======complete======");
        logger.info("======task.getId()：{}======",task.getId());
        taskService.complete(task.getId() );
        logger.info("======complete======");

        logger.info("======查看任务task2======");
        Task task2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        logger.info("ID:{}，姓名:{},接收人:{},开始时间：{},processInstanceId:{}",
                task2.getId(),
                task2.getName(),
                task2.getAssignee(),
                task2.getCreateTime(),
                processInstance.getId());
        logger.info("======查看任务task2======");
        //接受封装参数
        FlCorrelation flCorrelation = new FlCorrelation();
        flCorrelation.setId(processInstance.getId());
        flCorrelation.setBusinessId("bus-id");
        String nextNodeId = "Pro1,Pro2";
        String nextNodeName = "拉拉";
        String taskId = task2.getId();

        flCorrelation.setNextNodeId(nextNodeId);
        flCorrelation.setNextNodeName(nextNodeName);
        //先这样传吧
        flCorrelation.setTaskId(taskId);

        flLogs.setId(UUID.randomUUID().toString());
        flLogs.setFlCorrelId(flCorrelation.getId());
        flLogs.setCreatTime(new Date());
        flLogs.setCommitTime(new Date());

        flCorrelationMapper.insert(flCorrelation);
        flLogsMapper.insert(flLogs);
        taskService.addCandidateUser(taskId,"pro3");

    }

    @Test
    public void commit2(){
        //web参数
        FlLogs flLogs = new FlLogs();
        flLogs.setUserId("Pro1");
        flLogs.setUserName("Pro1");
        flLogs.setReason("可以");
        flLogs.setStatus("2");
        String flCorrelationid = "110014";

        //根据实例id查询当前的taskid
        logger.info("======查看任务======");
        Task task = taskService.createTaskQuery().processInstanceId(flCorrelationid).singleResult();
        logger.info("ID:{}，姓名:{},接收人:{},开始时间:{}",
                task.getId(),
                task.getName(),
                task.getAssignee(),
                task.getCreateTime());
        logger.info("======查看任务======");

        logger.info("======complete======");
        Map<String, Object> map = new HashMap<>();
        //audit 是像 bpmn sequenceFlow 传值
        map.put("audit",flLogs.getStatus());
        taskService.complete(task.getId(),map);
        logger.info("======complete======");


        Task task2 = taskService.createTaskQuery().processInstanceId(flCorrelationid).singleResult();
        if(null != task2){
            logger.info("======查看任务task2======");
            logger.info("ID:{}，姓名:{},接收人:{},开始时间:{}",
                    task2.getId(),
                    task2.getName(),
                    task2.getAssignee(),
                    task2.getCreateTime());
            logger.info("======查看任务task2======");
        }else{
            logger.info("======流程结束======");
        }
        //封装参数
        FlCorrelation flCorrelation = new FlCorrelation();
        flCorrelation.setNextNodeId("dep1,dep2");
        flCorrelation.setNextNodeName("");
        flCorrelation.setId(flCorrelationid);
        if(null != task2){
            flCorrelation.setTaskId(task2.getId());
        }else{
            flCorrelation.setTaskId("");
        }

        flLogs.setId(UUID.randomUUID().toString());
        flLogs.setFlCorrelId(flCorrelationid);
        flLogs.setCreatTime(new Date());
        flLogs.setCommitTime(new Date());
        flCorrelationMapper.updateByPrimaryKeySelective(flCorrelation);
        flLogsMapper.insert(flLogs);

    }

    @Test
    public void commit(){
        //web参数
        FlLogs flLogs = new FlLogs();
        flLogs.setUserId("Pro1");
        flLogs.setUserName("Pro1");
        flLogs.setReason("不同意");
        flLogs.setStatus("3");
        String flCorrelationid = "102501";

        //根据实例id查询当前的taskid
        logger.info("======查看任务======");
        Task task = taskService.createTaskQuery().processInstanceId(flCorrelationid).singleResult();
        logger.info("ID:{}，姓名:{},接收人:{},开始时间:{}",
                task.getId(),
                task.getName(),
                task.getAssignee(),
                task.getCreateTime());
        logger.info("======查看任务======");

        logger.info("======complete======");
        Map<String, Object> map = new HashMap<>();
        //audit 是像 bpmn sequenceFlow 传值
        map.put("audit",flLogs.getStatus());
        taskService.complete(task.getId(),map);
        logger.info("======complete======");


        Task task2 = taskService.createTaskQuery().processInstanceId(flCorrelationid).singleResult();
        if(null != task2){
            logger.info("======查看任务task2======");
            logger.info("ID:{}，姓名:{},接收人:{},开始时间:{}",
                    task2.getId(),
                    task2.getName(),
                    task2.getAssignee(),
                    task2.getCreateTime());
            logger.info("======查看任务task2======");
        }else{
            logger.info("======流程结束======");
        }
        //封装参数
        FlCorrelation flCorrelation = new FlCorrelation();
        flCorrelation.setNextNodeId("dep1,dep2");
        flCorrelation.setNextNodeName("");
        flCorrelation.setId(flCorrelationid);
        if(null != task2){
            flCorrelation.setTaskId(task2.getId());
        }else{
            flCorrelation.setTaskId("");
        }

        flLogs.setId(UUID.randomUUID().toString());
        flLogs.setFlCorrelId(flCorrelationid);
        flLogs.setCreatTime(new Date());
        flLogs.setCommitTime(new Date());
        flCorrelationMapper.updateByPrimaryKeySelective(flCorrelation);
        flLogsMapper.insert(flLogs);

    }
}
