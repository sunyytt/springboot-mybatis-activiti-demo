package com.example.demo.controller;


import com.example.demo.common.result.ResultPager;
import com.example.demo.model.User;
import com.example.demo.service.user.UserService;
import com.example.demo.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 1.@Controller和@RestController的区别
 * 1.1使用@Controller 注解，在对应的方法上，视图解析器可以解析return 的jsp,html页面，并且跳转到相应页面
 *  若返回json等内容到页面，则需要加@ResponseBody注解
 * 1.2@RestController注解，相当于@Controller+@ResponseBody两个注解的结合，
 *  返回json数据不需要在方法前面加@ResponseBody注解了，但使用@RestController这个注解，
 *  就不能返回jsp,html页面，视图解析器无法解析jsp,html页面
 */

/**
 * restful 风格
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public Object findAllUsers(
            @RequestParam(name = "current", required = false, defaultValue = "1")
                    int current,
            @RequestParam(name= "pageSize",required = false,defaultValue ="10" )
                    int pageSize
    ){
        ResultPager<User> pager = userService.findAllUser(current,pageSize);
        return ResponseResult.SuccessResult(pager);
    }
    @GetMapping("/{id}")
    public Object getUserById(@PathVariable Integer id){
        User user = userService.getUserById(id);
        return ResponseResult.SuccessResult(user);
    }
    @GetMapping("/user/{name}")
    public Object getUserByName(@PathVariable String name){
        User user = userService.getUserByName(name);
        return ResponseResult.SuccessResult(user);
    }
}
