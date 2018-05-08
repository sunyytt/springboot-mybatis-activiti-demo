package com.example.demo.controller;


import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activiti")
public class ActivitiController {
    private static Logger logger = LoggerFactory.getLogger(ActivitiController.class);

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    /**
     * api:https://www.activiti.org/javadocs/index.html
     * 简单介绍下上图用到的组件分别代表什么含义：
     开始事件（StartEvent）：流程的开始
     业务任务（ServiceTask）：表示一个业务操作，比如上图修改状态为进行中或者是已完成（也可以用0/1表示）
     用户任务（UserTask）：审批人，参与人，交与谁进行流程的继续。主要有id，name等属性
     排它网关（ExclusiveGateway）：该网关可以看作是一个开关或者是阀门。它只能选择一条可以继续下去的流程路线。不管申请被谁驳回都是直接结束。
     顺序流（SequenceFlow）：主要有id，name和condition。condition表示满足该顺序流执行的条件表达式。
     */

    @GetMapping
    public void fristDemo(){
//如果需要手动部署的话，核心代码如下：
//需要一个RepositoryService实例去调用方法，读取类路径下processes目录下面的MyProcess.bpmn文件，进行部署。
// 不管是我们手动部署，还是它已经自动帮我们部署了，我们都可以通过检查库表[act_ge_bytearray]中的记录来确定。
// 如果流程已经被发布出去，那么在该表中会生成两条记录。一个是bpmn结尾的文件名，一个是生成的.png结尾的流程图。而且多次发布同一个流程，会生成不同的版本号，来作为区分。（DEPLOYMENT_ID_）
//        Deployment deployment = repositoryService.createDeployment().addClasspathResource("processes/TestProcess.bpmn").deploy();
//        //获取流程定义
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        //启动流程定义，返回流程实例
//        ProcessInstance pi = runtimeService.startProcessInstanceById(processDefinition.getId());
        //自动部署之后直接获取流程
//        注意启动流程引擎的时候传入的key要和流程配置文件中的id保持一致。（以xml文件格式打开查看）
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("TestProcess");
        String processId = pi.getId();
        logger.info("流程创建成功，当前流程实例ID:{}",processId);

        Task task=taskService.createTaskQuery().processInstanceId(processId).singleResult();
        logger.info("第一次执行前，任务名称:{}",task.getName());
        taskService.complete(task.getId());

        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        logger.info("第二次执行前，任务名称:{}",task.getName());
        taskService.complete(task.getId());

        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        logger.info("task为null，任务执行完毕:{}",task);

    }
}
