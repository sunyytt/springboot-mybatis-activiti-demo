package com.example.demo.common;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ActivitiService {
    //注入为我们自动配置好的服务
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
/**
 * https://blog.csdn.net/fanxiangru999/article/category/7404549
 * https://my.oschina.net/silenceyawen/blog/1609603
 * https://my.oschina.net/u/930279/blog/793281
 */

    /**
     * 开始流程
     * @param processDefinitionKey bpmn id
     * @param variables  可以传任何值 可以为空
     */
    public void startProcess(String processDefinitionKey, Map<String, Object> variables) {
        runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    }

    /**
     *开始流程
     * @param processDefinitionKey bpmn id
     * @param businessKey  一个唯一标识上下文或给定流程定义中的流程实例的键。
     */
    public void startProcess(String processDefinitionKey, String businessKey  ) {
        runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey);
    }
    /**
     *获得某个人的任务别表
     * @param assignee
     * @return List<Task>
     */
        public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskCandidateUser(assignee).list();
    }
    /**
     * 根据审批人id查询需要审批的任务
     * @param userId
     * @return List<Task>
     */
    public List<Task> findTaskByUserId(String userId) {
        return taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();
    }

    //完成任务
    public void completeTasks(Boolean joinApproved, String taskId) {
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("joinApproved", joinApproved);
        taskService.complete(taskId, taskVariables);
    }

    /**
     * 审批任务
     * @param taskId 审批的任务id
     * @param userId 审批人的id
     * @param audit  审批意见：通过（pass）or驳回（reject）
     */
    public void completeTask(String taskId,String userId,String audit) {
        Map<String, Object> map = new HashMap<>();
        //1、认领任务
        taskService.claim(taskId, userId);
        //2.完成任务
        map.put("audit",audit);
        taskService.complete(taskId, map);
    }
}
