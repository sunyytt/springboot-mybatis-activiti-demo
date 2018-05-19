package com.example.demo.controller;

import com.example.demo.utils.ResponseResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

//    @GetMapping("/helloAdmin")
//    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
//    public Object admin(){
//        return ResponseResult.SuccessResult("admin:permission require edit,view");
//    }
//
    @GetMapping("/adminRoles")
    @RequiresRoles("admin")
    public Object adminRoles(){
        return ResponseResult.SuccessResult("adminRoles:permission require edit,view");
    }
//
//    @GetMapping("/helloUser")
//    @RequiresRoles("user")
//    public Object user(){
//        return ResponseResult.SuccessResult("user:permission require view");
//    }

}
