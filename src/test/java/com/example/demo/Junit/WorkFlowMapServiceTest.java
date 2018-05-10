package com.example.demo.Junit;

import com.example.demo.DemoApplication;
import com.example.demo.dao.FlCorrelationMapper;
import com.example.demo.dao.FlLogsMapper;
import com.example.demo.model.FlCorrelation;
import com.example.demo.model.FlLogs;
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
import org.springframework.util.StringUtils;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class WorkFlowMapServiceTest {
    private static Logger logger = LoggerFactory.getLogger(WorkFlowMapServiceTest.class);

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
     * 测试变量
     * act_run_identitylink 这张表会把能审批的人都显示出来
     */

    @Test
    public void apply(){
        //web参数
        FlLogs flLogs = new FlLogs();
        flLogs.setUserId("applyID");
        flLogs.setUserName("王刘");
        flLogs.setReason("离职：世界那么大，我想去看看。");
        flLogs.setStatus("1");

        // 自动执行与Key相对应的流程的最高版本
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("userId", flLogs.getUserName());
        variables.put("proMangerIds", "项目经理1,项目经理2");
        variables.put("mangerIds", "部门经理1,部门经理1");
        //发布流程 加入variables
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TestMapProcess",variables);

        //根据assignee
        logger.info("======查看["+flLogs.getUserName()+"]个人任务======");
        String assignee = flLogs.getUserName();
        //查看我的个人任务 按创建时间排序 升序
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(assignee)
                .orderByTaskCreateTime().desc()
                .list();
         for(Task t:tasks){
             logger.info("ID:{}，姓名:{},接收人:{},开始时间：{},processInstanceId:{}",
                     t.getId(),
                     t.getName(),
                     t.getAssignee(),
                     t.getCreateTime(),
                     processInstance.getId());
        }
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
        String nextNodeId = "项目经理1,项目经理2";
        String nextNodeName = "先不放";
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
    }

    @Test
    public void commit(){
        //web参数
        FlLogs flLogs = new FlLogs();
        flLogs.setUserId("项目经理1");
        flLogs.setUserName("项目经理1");
        flLogs.setReason("不同意");
        flLogs.setStatus("3");
        String flCorrelationid = "95001";

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
        flCorrelation.setNextNodeId("部门经理1,部门经理2");
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
    public void commit2(){
        //web参数
        FlLogs flLogs = new FlLogs();
        flLogs.setUserId("部门经理1");
        flLogs.setUserName("部门经理1");
        flLogs.setReason("同意");
        flLogs.setStatus("2");
        String flCorrelationid = "72501";

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
        }
        //封装参数
        FlCorrelation flCorrelation = new FlCorrelation();
        flCorrelation.setNextNodeId("");
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
