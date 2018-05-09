package com.example.demo.service.leaveInfo.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LeaveMapper;
import com.example.demo.model.LeaveInfo;
import com.example.demo.service.leaveInfo.TestLeaveService;
import com.example.demo.service.leaveInfo.LeaveService;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	private TestLeaveService  testLeaveService;
	@Autowired
	private LeaveMapper leaveMapper;
	@Autowired
	private RuntimeService runtimeService;
	@Override
	public void addLeaveAInfo(String msg) {
		LeaveInfo leaveInfo = new LeaveInfo();
		leaveInfo.setLeaveMsg(msg);
		/**
		 * 关于id
		 * （1）单实例或者单节点组：
		 * 经过500W、1000W的单机表测试，自增ID相对UUID来说，自增ID主键性能高于UUID，磁盘存储费用比UUID节省一半的钱。所以在单实例上或者单节点组上，使用自增ID作为首选主键。
		 * （2）分布式架构场景：
		 *20个节点组下的小型规模的分布式场景，为了快速实现部署，可以采用多花存储费用、牺牲部分性能而使用UUID主键快速部署；
		 * 20到200个节点组的中等规模的分布式场景，可以采用自增ID+步长的较快速方案。
		 * 200以上节点组的大数据下的分布式场景，可以借鉴类似twitter雪花算法构造的全局自增ID作为主键。
		 */
		String id = UUID.randomUUID().toString();
		leaveInfo.setId(id);
		//新增一条记录至数据库中
		leaveMapper.insert(leaveInfo);
		//启动流程引擎
		testLeaveService.startProcess(id);
	}

	@Override
	public List<LeaveInfo> getByUserId(String userId) {
		ArrayList<LeaveInfo> leaveInfoList = new ArrayList<>();
//		根据审批人id查询需要审批的任务
		List<Task> list = testLeaveService.findTaskByUserId(userId);
		for (Task task : list) {
			ProcessInstance result = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			//获得业务流程的bussinessKey
			String businessKey = result.getBusinessKey();
			LeaveInfo leaveInfo = leaveMapper.getById(businessKey);
			leaveInfo.setTaskId(task.getId());
			leaveInfoList.add(leaveInfo);
		}
		return leaveInfoList;
	}

	@Override
	public void completeTaskByUser(String taskId, String userId, String audit) {
		testLeaveService.completeTaskByUser(taskId, userId, audit);
	}

}
