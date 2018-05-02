package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 具体见：https://www.cnblogs.com/ilinuxer/p/6444804.html
 * RESTful API具体设计如下：
 * 请求类型	    URL	        功能说明
 * GET	        /users	    查询用户列表
 * POST	        /users	    创建一个用户
 * GET	        /users/id	根据id查询一个用户
 * PUT	        /users/id	根据id更新一个用户
 * DELETE	    /users/id	根据id删除一个用户
 */
@RestController
@RequestMapping(value = "/users")
public class RestfulUserController {
    @Autowired
    private UserService userService;

    /**
     * post
     * url=/users/
     * @param user
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postUser(User user){
        userService.addUser(user);
        return "success";
    }

    /**
     * get
     * url=users/1
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id){
        return userService.getUserById(id);
    }


}
