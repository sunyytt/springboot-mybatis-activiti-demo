package com.example.demo.service.userTest;

import com.example.demo.common.result.ResultPager;
import com.example.demo.model.UserTest;

public interface UserTestService {

    ResultPager<UserTest> findAllUser(int current, int pageSize);

    void addUser(UserTest userTest);
}
