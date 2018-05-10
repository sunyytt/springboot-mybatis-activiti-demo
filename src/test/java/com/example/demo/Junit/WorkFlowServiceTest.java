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

import java.util.Date;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class WorkFlowServiceTest {
    private static Logger logger = LoggerFactory.getLogger(WorkFlowServiceTest.class);

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


    @Test
    public void apply(){
        //web参数
        FlLogs flLogs = new FlLogs();
        flLogs.setUserId("applyID");
        flLogs.setUserName("张三");
        flLogs.setReason("请年假3天");
        flLogs.setStatus("1");

        //发布流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TestProcess");

        //根据实例id查询当前的taskid
        logger.info("======查看任务======");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        logger.info("ID:{}，姓名:{},接收人:{},开始时间",
                task.getId(),
                task.getName(),
                task.getAssignee(),
                task.getCreateTime());
        logger.info("======查看任务======");

        logger.info("======complete======");
        taskService.complete(task.getId() );
        logger.info("======complete======");

        logger.info("======查看任务task2======");
        Task task2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        logger.info("ID:{}，姓名:{},接收人:{},开始时间",
                task2.getId(),
                task2.getName(),
                task2.getAssignee(),
                task2.getCreateTime());
        logger.info("======查看任务task2======");
        //接受封装参数
        FlCorrelation flCorrelation = new FlCorrelation();
        flCorrelation.setId(processInstance.getId());
        flCorrelation.setBusinessId("bus-id");
        flCorrelation.setNextNodeId("nextid");
        flCorrelation.setNextNodeName("老板");
        flCorrelation.setTaskId(task2.getId());

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
        flLogs.setUserId("nextid");
        flLogs.setUserName("老板");
        flLogs.setReason("同意");
        flLogs.setStatus("2");
        String flCorrelationid = "37501";

        //根据实例id查询当前的taskid
        logger.info("======查看任务======");
        Task task = taskService.createTaskQuery().processInstanceId(flCorrelationid).singleResult();
        logger.info("ID:{}，姓名:{},接收人:{},开始时间",
                task.getId(),
                task.getName(),
                task.getAssignee(),
                task.getCreateTime());
        logger.info("======查看任务======");

        logger.info("======complete======");
        taskService.complete(task.getId() );
        logger.info("======complete======");


        Task task2 = taskService.createTaskQuery().processInstanceId(flCorrelationid).singleResult();
        if(null != task2){
            logger.info("======查看任务task2======");
            logger.info("ID:{}，姓名:{},接收人:{},开始时间",
                    task2.getId(),
                    task2.getName(),
                    task2.getAssignee(),
                    task2.getCreateTime());
            logger.info("======查看任务task2======");
//            flCorrelation.setTaskId(task2.getId());
        }else {
//            flCorrelation.setTaskId("");
        }
        //封装参数
        FlCorrelation flCorrelation = new FlCorrelation();
        flCorrelation.setNextNodeId("");
        flCorrelation.setNextNodeName("");
        flCorrelation.setId(flCorrelationid);
        flCorrelation.setTaskId("");
        flCorrelation.setId(flCorrelationid);

        flLogs.setId(UUID.randomUUID().toString());
        flLogs.setFlCorrelId(flCorrelationid);
        flLogs.setCreatTime(new Date());
        flLogs.setCommitTime(new Date());
        flCorrelationMapper.updateByPrimaryKeySelective(flCorrelation);
        flLogsMapper.insert(flLogs);

    }

}
