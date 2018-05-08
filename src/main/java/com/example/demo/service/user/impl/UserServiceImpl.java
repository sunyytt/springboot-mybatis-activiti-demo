package com.example.demo.service.user.impl;

import com.example.demo.common.result.ResultPager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResultPager<User> findAllUser(int current, int pageSize) {
        ResultPager.setPager(current,pageSize);
        List<User> list =  userMapper.selectUsers();
        ResultPager<User> pager = new ResultPager<User>(list);
        return pager;
    }
    @Override
    public User getUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }
    @Override
    public User getUserByName(String name) {

//        return userMapper.getUserByName(name);
        return null;
    }


}
