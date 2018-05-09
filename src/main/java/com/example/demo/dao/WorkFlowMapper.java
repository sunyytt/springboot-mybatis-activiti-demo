package com.example.demo.dao;

import com.example.demo.model.WorkFlow;

public interface WorkFlowMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkFlow record);

    int insertSelective(WorkFlow record);

    WorkFlow selectByPrimaryKey(String id);

    WorkFlow selectByUserId(String UserId);

    int updateByPrimaryKeySelective(WorkFlow record);

    int updateByPrimaryKey(WorkFlow record);

}