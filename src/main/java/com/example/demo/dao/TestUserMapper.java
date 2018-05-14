package com.example.demo.dao;

import com.example.demo.model.TestUser;

public interface TestUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(TestUser record);

    int insertSelective(TestUser record);

    TestUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TestUser record);

    int updateByPrimaryKey(TestUser record);
}