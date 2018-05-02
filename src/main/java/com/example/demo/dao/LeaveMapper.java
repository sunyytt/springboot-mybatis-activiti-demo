package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.LeaveInfo;

public interface LeaveMapper {

	int getCount();
	
	void insert(LeaveInfo entity);
	
	void delete(String id);
	
	LeaveInfo getById(String id);
	
	List<LeaveInfo> getList();
	
	void update(LeaveInfo entity);
}
