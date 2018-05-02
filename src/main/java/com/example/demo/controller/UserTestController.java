package com.example.demo.controller;

import com.example.demo.service.userTest.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本类为react接口
 */
@RestController
@RequestMapping("/userTest")
public class UserTestController {
    @Autowired
    private UserTestService userTestService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Object getUsers(
        @RequestParam(name = "current", required = false, defaultValue = "1")
        int current,
        @RequestParam(name = "pageSize", required = false, defaultValue = "10")
        int pageSize) {
        return userTestService.findAllUser(current,pageSize);
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public void addUser(){
//        return userTestService.
    }
}
