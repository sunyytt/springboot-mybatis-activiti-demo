package com.example.demo.service.userTest.impl;

import com.example.demo.common.result.ResultPager;
import com.example.demo.dao.UserTestMapper;
import com.example.demo.model.UserTest;
import com.example.demo.service.userTest.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserTestServiceImpl implements UserTestService {
    @Autowired
    private UserTestMapper userTestMapper;
    @Override
    public ResultPager<UserTest> findAllUser(int current, int pageSize) {
        ResultPager.setPager(current,pageSize);
        List<UserTest> userTestList =userTestMapper.selectUsers();
        ResultPager<UserTest> resultPager = new ResultPager<UserTest>(userTestList);
        return resultPager;
    }
    @Override
    public void addUser(UserTest userTest) {
        userTestMapper.insert(userTest);

    }
}
