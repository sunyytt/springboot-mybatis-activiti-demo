package com.example.demo.dao;

import com.example.demo.model.FlCorrelation;
import com.example.demo.model.FlCorrelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FlCorrelationMapper {
    int countByExample(FlCorrelationExample example);

    int deleteByExample(FlCorrelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(FlCorrelation record);

    int insertSelective(FlCorrelation record);

    List<FlCorrelation> selectByExample(FlCorrelationExample example);

    FlCorrelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FlCorrelation record, @Param("example") FlCorrelationExample example);

    int updateByExample(@Param("record") FlCorrelation record, @Param("example") FlCorrelationExample example);

    int updateByPrimaryKeySelective(FlCorrelation record);

    int updateByPrimaryKey(FlCorrelation record);
}