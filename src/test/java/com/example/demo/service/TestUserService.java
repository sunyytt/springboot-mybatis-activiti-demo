package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserService {
    @Autowired
    UserService userService;

    @Test
    public void getUserByName(){
        User user = userService.getUserByName("smith");
        System.out.println(user.getPermission());
    }

}
