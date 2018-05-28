package com.example.demo.dao;

import com.example.demo.model.FlLogs;
import com.example.demo.model.FlLogsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FlLogsMapper {
    int countByExample(FlLogsExample example);

    int deleteByExample(FlLogsExample example);

    int deleteByPrimaryKey(String id);

    int insert(FlLogs record);

    int insertSelective(FlLogs record);

    List<FlLogs> selectByExample(FlLogsExample example);

    FlLogs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FlLogs record, @Param("example") FlLogsExample example);

    int updateByExample(@Param("record") FlLogs record, @Param("example") FlLogsExample example);

    int updateByPrimaryKeySelective(FlLogs record);

    int updateByPrimaryKey(FlLogs record);
}