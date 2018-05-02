package com.example.demo.service.userTest;

import com.example.demo.model.UserTest;
import com.example.demo.utils.ResultPager;

public interface UserTestService {

    ResultPager<UserTest> findAllUser(int current, int pageSize);

    void addUser(UserTest userTest);
}
