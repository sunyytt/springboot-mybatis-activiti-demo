package com.example.demo.service.workflow.impl;

import com.example.demo.dao.WorkFlowMapper;
import com.example.demo.model.WorkFlow;
import com.example.demo.service.workflow.WorkFlowService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 1.申请人申请
 * 2.审核人审批
 * 3.审核人代办任务
 * 4.申请人/流程上的人，能看到流程位置
 *
 */

@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    private static Logger logger = LoggerFactory.getLogger(WorkFlowServiceImpl.class);

    @Autowired
    private WorkFlowMapper workFlowMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;

    //https://blog.csdn.net/a67474506/article/details/38266129
    public void startFlow(WorkFlow workFlow){
        String id = UUID.randomUUID().toString();
        workFlow.setId(id);
        workFlow.setStatus("1");
        workFlow.setCreatTime(new Date());
        workFlowMapper.insert(workFlow);
        logger.info("======发布流程 =======");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TestProcess", id);
        logger.info("id:{} activitiId:{}",processInstance.getId(),processInstance.getActivityId());
        logger.info("======发布流程 =======");
    }
    /**
     * 查看任务
     */
    public void queryTask(){
        logger.info("======查看任务======");
        //根据接受人获取该用户的任务
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("张三").list();
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
    public void startTask(){
        logger.info("======提出请假申请，启动流程======");
        //taskId 就是查询任务中的 ID task.getId(),
        String taskId = "204";
        //完成请假申请任务
        taskService.complete(taskId );
        logger.info("======提出请假申请，启动流程======");
    }

    //老板查看任务 ，并审批请假
    public void queryTask2() {
        //根据接受人获取该用户的任务
        logger.info("======老板查看任务 ，并审批请假======");
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("老板").list();
        for (Task task : tasks) {
            logger.info("ID:{}，姓名:{},接收人:{},开始时间",
                    task.getId(),
                    task.getName(),
                    task.getAssignee(),
                    task.getCreateTime());
        }
        logger.info("======老板查看任务 ，并审批请假======");
    }

    //流程完毕，可以再act_hi_actinst表中看到整个请假流程
    public void startTask2(){
        //taskId 就是查询任务中的 ID
        String taskId = "302";
        //完成请假申请任务
        taskService.complete(taskId );
    }
//    public List<WorkFlow> queryFlowByUserId(String userId){
//        List<WorkFlow> flows = new ArrayList<WorkFlow>();
////        WorkFlow workFlow = new WorkFlow();
////        workFlow = workFlowMapper.selectByPrimaryKey(userId);
////        flows.add(workFlow);
//        List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();
//        for (Task task : list) {
//            ProcessInstance result = runtimeService.createProcessInstanceQuery().
//                    processInstanceId(task.getProcessInstanceId()).singleResult();
//            //获得业务流程的bussinessKey 也就是 id
//            String businessKey = result.getBusinessKey();
//            WorkFlow workFlow = workFlowMapper.selectByPrimaryKey(businessKey);
//            workFlow.setTaskId(task.getId());
//            flows.add(workFlow);
//        }
//        return flows;
//    }
//
//    public void updateById(WorkFlow workFlow){
//        workFlowMapper.updateByPrimaryKeySelective(workFlow);
//    }
//
//    //审批
//    public void completeTask(WorkFlow workFlow){
////        workFlow.setApprovalId("");
////        workFlow.setApprovalTime(new Date());
////        workFlowMapper.updateByPrimaryKeySelective(workFlow);
////        Map<String, Object> map = new HashMap<>();
////        //1、认领任务
////        taskService.claim(taskId, userId);
////        //2.完成任务
////        map.put("audit",audit);
////        taskService.complete(taskId, map);
//    }

}
