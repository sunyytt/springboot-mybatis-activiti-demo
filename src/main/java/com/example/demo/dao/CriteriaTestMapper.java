package com.example.demo.dao;

import com.example.demo.model.CriteriaTest;
import com.example.demo.model.CriteriaTestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CriteriaTestMapper {
    int deleteByExample(CriteriaTestExample example);

    int deleteByPrimaryKey(String id);

    int insert(CriteriaTest record);

    int insertSelective(CriteriaTest record);

    List<CriteriaTest> selectByExample(CriteriaTestExample example);

    CriteriaTest selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CriteriaTest record, @Param("example") CriteriaTestExample example);

    int updateByExample(@Param("record") CriteriaTest record, @Param("example") CriteriaTestExample example);

    int updateByPrimaryKeySelective(CriteriaTest record);

    int updateByPrimaryKey(CriteriaTest record);
}