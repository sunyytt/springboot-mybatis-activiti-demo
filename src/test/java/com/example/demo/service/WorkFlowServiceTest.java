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
        flLogs.setNextNode("admin");
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
        flLogs.setUserId("李四");
        flLogs.setUserName("李四");
        flLogs.setNextNode("admin");
        flLogs.setFlCorrelId("142501");

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
        ResultPager<FlLogs> pager = workFlowService.queryMyApproves(1,10,flLogs,"部门经理");
        pager.getList().forEach(f->logger.info("userId:{}，status:{}",f.getUserId(),f.getStatus()));
    }


    @Test
    public void findMyTaskList(){
        String userId = "李四";
        List<Task> list = taskService
                .createTaskQuery()
                .taskAssignee(userId)//指定个人任务查询
                .list();
//        list.forEach(task ->{
//            System.out.println("id="+task.getId());
//            System.out.println("name="+task.getName());
//            System.out.println("assinee="+task.getAssignee());
//            System.out.println("assinee="+task.getCreateTime());
//            System.out.println("executionId="+task.getExecutionId());
//        } );
        for(Task task:list ){
            System.out.println("id="+task.getId());
            System.out.println("name="+task.getName());
            System.out.println("assinee="+task.getAssignee());
            System.out.println("assinee="+task.getCreateTime());
            System.out.println("executionId="+task.getExecutionId());
            System.out.println("##################################");

        }

    }

    //查询组任务列表
    @Test
    public void findGroupList(){
        String userId = "李四";//小张，小李可以查询结果，小王不可以，因为他不是部门经理
        List<Task> list = taskService//
                .createTaskQuery()//
                .taskCandidateUser(userId)//指定组任务查询
                .list();

        for(Task task:list ){
            System.out.println("id="+task.getId());
            System.out.println("name="+task.getName());
            System.out.println("assinee="+task.getAssignee());
            System.out.println("assinee="+task.getCreateTime());
            System.out.println("executionId="+task.getExecutionId());
            System.out.println("##################################");

        }

    }

    //查询组任务成员列表
    @Test
    public void findGroupUser(){
        String taskId = "130025";
        List<IdentityLink> list = taskService//
                .getIdentityLinksForTask(taskId);
        for(IdentityLink identityLink:list ){
            System.out.println("userId="+identityLink.getUserId());
            System.out.println("taskId="+identityLink.getTaskId());
            System.out.println("piId="+identityLink.getProcessInstanceId());
            System.out.println("######################");
        }

    }
    @Test
    public void queryTask(){
        logger.info("======查看任务======");
        String processInstanceId="135001";
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        logger.info("ID:{}，姓名:{},接收人:{},开始时间：{}",
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
        String name="部门经理";
        //根据接受人获取该用户的任务   代办任务
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(name).list();
        for (Task task : tasks) {
            logger.info("ID:{}，姓名:{},接收人:{},开始时间:{}",
                    task.getId(),
                    task.getName(),
                    task.getAssignee(),
                    task.getCreateTime());
        }
        logger.info("======查看任务======");
    }



}
