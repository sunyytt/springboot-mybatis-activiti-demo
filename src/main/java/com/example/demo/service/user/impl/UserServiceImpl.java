package com.example.demo.service.user.impl;


import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取所有用户列表
     * @return
     */
    public List<UserInfo> getUserList(){
        List<UserInfo> userList=new ArrayList<UserInfo>();
        userList=userRepository.findAll();
        return  userList;
    }

    /**
     * 通过姓名获取用户信息
     * @param name 用户姓名
     * @return
     */
    public UserInfo getUserByName(String name) {
        return userRepository.findByName(name);
    }

    /**
     * 新增用户信息
     * @param userInfo 用户信息
     * @return
     */
    public UserInfo addUserInfo(UserInfo userInfo) {
        return userRepository.save(userInfo);
    }

    /**
     * 更新用户信息
     * @param userInfo 用户信息
     * @return
     */
    public UserInfo updateUserInfoById(UserInfo userInfo) {
        return userRepository.save(userInfo);
    }

    /**
     * 删除用户信息
     * @param id 主键Id
     */
    public void deleteUserInfoById(Long id) {
        userRepository.delete(id);
    }

    /**
     * 获取最新的用户
     * @return
     */
    public List<UserInfo> getCurrentUserList() {
        Sort sort=new Sort(Sort.Direction.DESC,"createTime");
        return userRepository.findAll(sort);

    }

    /**
     * 获取分页的用户
     * @return
     */
    public Page<UserInfo> getPageUserList() {
        Sort sort=new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable=new PageRequest(0,5,sort);
        return userRepository.findAll(pageable);
    }



}
