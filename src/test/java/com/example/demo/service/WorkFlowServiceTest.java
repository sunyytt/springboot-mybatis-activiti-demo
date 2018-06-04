package com.example.demo.service;

import com.example.demo.common.result.ResultPager;
import com.example.demo.model.FlLogs;
import com.example.demo.service.workflow.WorkFlowService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkFlowServiceTest {
    private static Logger logger = LoggerFactory.getLogger(WorkFlowServiceTest.class);
    @Autowired
    WorkFlowService workFlowService;

    @Autowired
    private TaskService taskService;

    @Test
    public void save(){
        FlLogs flLogs = new FlLogs();
        flLogs.setReason("TEST");
        flLogs.setUserId("test");
        flLogs.setUserName("name");
        flLogs.setNextNode("admin");
        workFlowService.save(flLogs);
    }

    @Test
    public  void commitNotSave(){
        FlLogs flLogs = new FlLogs();
        flLogs.setReason("TEST");
        flLogs.setUserId("test");
        flLogs.setUserName("name");
        flLogs.setNextNode("role1");
        workFlowService.apply(flLogs);
    }

    @Test
    public  void commitSave(){
        FlLogs flLogs = new FlLogs();
        flLogs.setId("edc34fb9-0978-4b9c-b708-f5d284229964");
        workFlowService.apply(flLogs);
    }
    @Test
    public void approves(){
        FlLogs flLogs = new FlLogs();
        flLogs.setStatus("2");
        flLogs.setReason("tongyi");
        flLogs.setUserId("范冰冰");
        flLogs.setUserName("范冰冰");
        flLogs.setNextNode("admin");
        flLogs.setFlCorrelId("10001");

        workFlowService.approves(flLogs);
    }

    @Test
    public void queryMyTask(){
        FlLogs flLogs = new FlLogs();
        flLogs.setUserId("test");
        ResultPager<FlLogs> pager = workFlowService.queryMyTask(1,10,flLogs);
        pager.getList().forEach(f->logger.info("status:{}",f.getStatus()));

    }
    @Test
    public void queryMyApproves(){
        FlLogs flLogs = new FlLogs();
        ResultPager<FlLogs> pager = workFlowService.queryMyApproves(1,10,flLogs,"role1");
        pager.getList().forEach(f->logger.info("userId:{}，status:{}",f.getUserId(),f.getStatus()));
    }
    @Test
    public void detail(){
        FlLogs flLogs = workFlowService.getFlLogsById("b19e7426-0660-41a0-8b3f-ad189b3770a5");
        System.out.println(flLogs.getFlCorrelId());
    }

}
