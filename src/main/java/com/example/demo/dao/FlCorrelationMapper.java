package com.example.demo.dao;

import com.example.demo.model.FlCorrelation;

public interface FlCorrelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlCorrelation record);

    int insertSelective(FlCorrelation record);

    FlCorrelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlCorrelation record);

    int updateByPrimaryKey(FlCorrelation record);
}