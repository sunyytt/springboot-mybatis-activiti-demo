package com.example.demo.dao;

import com.example.demo.model.FlLogs;

public interface FlLogsMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlLogs record);

    int insertSelective(FlLogs record);

    FlLogs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlLogs record);

    int updateByPrimaryKey(FlLogs record);
}