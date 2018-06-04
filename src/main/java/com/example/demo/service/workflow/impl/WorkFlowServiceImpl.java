package com.example.demo.service.workflow.impl;

import com.example.demo.common.result.ResultPager;
import com.example.demo.dao.FlCorrelationMapper;
import com.example.demo.dao.FlLogsMapper;
import com.example.demo.model.*;
import com.example.demo.service.workflow.WorkFlowService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 请假流程
 * 保存按钮 （未提交）： 1.insert fl_logs表 id,user_id,user_name，reason,status=0：未提交，creat_time
 * 申请按钮：1.insert 关联表 fl_correlation：记录业务id，processInstanceid，最新的taskID，下一个审批人，始终一条数据
 *         2.update 日志表 ： 主要更新 fl_correl_id，status=1：commit_time 等。提交申请，记录申请理由，审批理由，时间，状态等，[多条数据]
 *         3.发布流程 ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TestProcess");
 *         4.查询任务 Task t = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
 *         5.提交申请： taskService.complete(taskId );
 *
 * 审批人  ：1.【查询代办任务】：List<Task> tasks = taskService.createTaskQuery().taskAssignee(name).list();
 *          2.点击任务  查询关联表，显示业务记录
 *          3.完成同意/拒绝，点击提交按钮  更新关联表如下一个审批人，最新的taskID，insert 日志表
 *          4. 提交：taskService.complete(taskId );
 *          5.更新 fl_correlation 流程关联表 流程当前状态
 *
 *  【代办任务】：根据当前用户角色id  查询fl_correlation表next_node_id,current_status=1：提交申请的列表
 *  【我提交的任务】：查询fl_logs表 user_id=当前用户iD，status==0直接显示未提交， status==1 时 当前流程状态显示l_correlation表 current_status
 *
 * 获取所有节点 流程部署至服务器上之后可使用 可做进度条：
 * BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
 * 查询历史活动走过的节点：
 * List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
 *   .processInstanceId(processInstanceId).list();
 *
 */

@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    private static Logger logger = LoggerFactory.getLogger(WorkFlowServiceImpl.class);

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private FlLogsMapper flLogsMapper;
    @Autowired
    private FlCorrelationMapper flCorrelationMapper;


    public FlLogs getFlLogsById(String id){
       return flLogsMapper.selectByPrimaryKey(id);
    }

    public void save(FlLogs flLogs){
        String id = UUID.randomUUID().toString();
        flLogs.setId(id);
        flLogs.setStatus("0");
        flLogs.setCreatTime(new Date());
        flLogsMapper.insertSelective(flLogs);
    }

    public void update(FlLogs flLogs){
        flLogsMapper.updateByPrimaryKeySelective(flLogs);
    }

    public void delete(String id){
        flLogsMapper.deleteByPrimaryKey(id);
    }
    private List<String> getRoles(String roleId){
        Map<String,List> rolesMap = new HashMap<>();
        String[] role1 = {"鹿晗","范冰冰","冯小刚","Bigbang"};
        String[] role2 = {"小李","小王","小红"};
        rolesMap.put("role1",Arrays.asList(role1));
        rolesMap.put("role2",Arrays.asList(role2));
        if(rolesMap.containsKey(roleId)){
            return  rolesMap.get(roleId);
        }else{
            return null;
        }
    }

    @Transactional
    public FlLogs apply(FlLogs flLogs){
        // 自动执行与Key相对应的流程的最高版本
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("applyId", flLogs.getUserId());
        variables.put("roles", getRoles(flLogs.getNextNode()));
        /**在部署流程定义和启动流程实例的中间，设置组任务的办理人，向Activity表中存放组和用户的信息*/

        //IdentityService identityService = processEngine.getIdentityService();//认证：保存组和用户信息

//        identityService.saveGroup(new GroupEntity("部门经理"));//建立组
//        identityService.saveGroup(new GroupEntity("总经理"));//建立组
//
//        identityService.saveUser(new UserEntity("小张"));
//        identityService.saveUser(new UserEntity("小李"));
//        identityService.saveUser(new UserEntity("小王"));
//        identityService.saveUser(new UserEntity("小四"));
//
//        identityService.createMembership("小张", "部门经理");//建立组和用户关系
//        identityService.createMembership("小四", "部门经理");//建立组和用户关系
//        identityService.createMembership("小李", "部门经理");//建立组和用户关系
//        identityService.createMembership("小王", "总经理");//建立组和用户关系

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("workFlow",variables);
        String taskId = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult().getId();
        taskService.complete(taskId);
        FlCorrelation flCorrelation = new FlCorrelation();
        flCorrelation.setId(processInstance.getId());
        flCorrelation.setNextNodeId(flLogs.getNextNode());
        flCorrelation.setCurrentStatus("1");
        String taskId2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult().getId();
        flCorrelation.setTaskId(taskId2);
        flCorrelationMapper.insertSelective(flCorrelation);

        flLogs.setCommitTime(new Date());
        flLogs.setStatus("1");
        flLogs.setFlCorrelId(flCorrelation.getId());
        if(StringUtils.isNotEmpty(flLogs.getId())){
            flLogsMapper.updateByPrimaryKeySelective(flLogs);
        }else {
            String id = UUID.randomUUID().toString();
            flLogs.setId(id);
            flLogs.setCreatTime(new Date());
            flLogsMapper.insertSelective(flLogs);
        }

        return  flLogsMapper.selectByPrimaryKey(flLogs.getId());
    }

    @Transactional
    public void approves(FlLogs flLogs){
        String taskId = taskService.createTaskQuery().processInstanceId(flLogs.getFlCorrelId()).singleResult().getId();
        taskService.complete(taskId);
        String id = UUID.randomUUID().toString();
        flLogs.setId(id);
        flLogs.setCreatTime(new Date());
        flLogs.setCommitTime(new Date());
        flLogsMapper.insertSelective(flLogs);

        FlCorrelation flCorrelation = new FlCorrelation();
        flCorrelation.setId(flLogs.getFlCorrelId());
        flCorrelation.setCurrentStatus(flLogs.getStatus());
//        flCorrelation.setNextNodeId("");
//        flCorrelation.setNextNodeName("");
//        flCorrelation.setTaskId("");
        flCorrelationMapper.updateByPrimaryKeySelective(flCorrelation);
    }



    public ResultPager<FlLogs> queryMyTask(int current, int pageSize, FlLogs flLogs){
        ResultPager.setPager(current,pageSize);
        FlLogsExample flLogsExample = new FlLogsExample();
        //like criteriaTestExample.createCriteria().andIdEqualTo("1");
        List<String> values =  new ArrayList<String>();
        values.add("0");
        values.add("1");
        flLogsExample.createCriteria().andUserIdEqualTo(flLogs.getUserId())
                .andStatusIn(values);
        flLogsExample.setOrderByClause("status ASC");

        List<FlLogs> list = flLogsMapper.selectByExample(flLogsExample);
        for (FlLogs f:list){
            if(f.getStatus().equals("1")){
                FlCorrelationExample flCorrelationExample = new FlCorrelationExample();
                flCorrelationExample.createCriteria().andIdEqualTo(f.getFlCorrelId());
                List<FlCorrelation> flCorrelations = flCorrelationMapper.selectByExample(flCorrelationExample);
                f.setStatus(flCorrelations.get(0).getCurrentStatus());
            }
        }
        ResultPager<FlLogs> pager = new ResultPager<FlLogs>(list);
        return pager;
    }

    public ResultPager<FlLogs> queryMyApproves(int current, int pageSize, FlLogs flLogs,String role){
        ResultPager.setPager(current,pageSize);
        FlLogsExample flLogsExample = new FlLogsExample();
        FlCorrelationExample flCorrelationExample = new FlCorrelationExample();
        flCorrelationExample.createCriteria().andNextNodeIdEqualTo(role).andCurrentStatusEqualTo("1");
        List<FlCorrelation> flCorrelations = flCorrelationMapper.selectByExample(flCorrelationExample);
        List<String> values =  new ArrayList<String>();
        for(FlCorrelation f:flCorrelations){
            values.add(f.getId());
        }
        List<FlLogs> list = new ArrayList<FlLogs>();
        if(!values.isEmpty()){
            flLogsExample.createCriteria().andFlCorrelIdIn(values);
            list = flLogsMapper.selectByExample(flLogsExample);
        }
        ResultPager<FlLogs> pager = new ResultPager<FlLogs>(list);
        return pager;
    }




    //https://blog.csdn.net/a67474506/article/details/38266129
//    public void startFlow(WorkFlow workFlow){
//        String id = UUID.randomUUID().toString();
//        workFlow.setId(id);
//        workFlow.setStatus("1");
//        workFlow.setCreatTime(new Date());
//        workFlowMapper.insert(workFlow);
//        logger.info("======发布流程 =======");
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TestProcess", id);
//        logger.info("id:{} activitiId:{}",processInstance.getId(),processInstance.getActivityId());
//        logger.info("======发布流程 =======");
//    }
//    /**
//     * 查看任务
//     */
//    public void queryTask(){
//        logger.info("======查看任务======");
//        //根据接受人获取该用户的任务
//        List<Task> tasks = taskService.createTaskQuery().taskAssignee("张三").list();
//        for (Task task : tasks) {
//            logger.info("ID:{}，姓名:{},接收人:{},开始时间",
//                    task.getId(),
//                    task.getName(),
//                    task.getAssignee(),
//                    task.getCreateTime());
//        }
//        logger.info("======查看任务======");
//    }
//    //提出请假申请，启动流程
//    public void startTask(){
//        logger.info("======提出请假申请，启动流程======");
//        //taskId 就是查询任务中的 ID task.getId(),
//        String taskId = "204";
//        //完成请假申请任务
//        taskService.complete(taskId );
//        logger.info("======提出请假申请，启动流程======");
//    }
//
//    //老板查看任务 ，并审批请假
//    public void queryTask2() {
//        //根据接受人获取该用户的任务
//        logger.info("======老板查看任务 ，并审批请假======");
//        List<Task> tasks = taskService.createTaskQuery().taskAssignee("老板").list();
//        for (Task task : tasks) {
//            logger.info("ID:{}，姓名:{},接收人:{},开始时间",
//                    task.getId(),
//                    task.getName(),
//                    task.getAssignee(),
//                    task.getCreateTime());
//        }
//        logger.info("======老板查看任务 ，并审批请假======");
//    }
//
//    //流程完毕，可以再act_hi_actinst表中看到整个请假流程
//    public void startTask2(){
//        //taskId 就是查询任务中的 ID
//        String taskId = "302";
//        //完成请假申请任务
//        taskService.complete(taskId );
//    }
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
