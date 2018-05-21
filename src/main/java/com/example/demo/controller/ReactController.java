package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.user.UserService;
import com.example.demo.utils.JWTUtil;
import com.example.demo.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReactController {
    private static final Logger log = LoggerFactory.getLogger(ReactController.class);

    @Autowired
    private UserService userService;

    /**
     * @RequestParam  ：
     * @param current
     * @param pageSize
     * @return
     */
    @GetMapping("/rule")
    public Object getAllUsers( @RequestParam(name = "currentPage", required = true, defaultValue = "1")
                                           int current,
                               @RequestParam(name= "pageSize",required = true,defaultValue ="10" )
                                           int pageSize){
        log.info("=====current:{},pageSize:{}",current,pageSize);

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(int i=0;i<=20;i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",i);
            map.put("title","拉拉"+i);
            map.put("no","啦啦啦"+i);
            list.add(map);
        }
        Map<String,Object> pagination = new HashMap<String,Object>();
        pagination.put("total",list.size());
        pagination.put("pageSize",pageSize);
        pagination.put("current",current);

        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("list",list);
        resultMap.put("pagination",pagination);

        return  resultMap;
    }

    /**
     * @RequestBody 注解的含义就是读取请求body里面的值映射成参数 ,是因为 content-type: application/json;charset=UTF-8
     * @ResponseBody: 这个注解会直接把返回的对象以json形式写在body里
     * @param user
     * @return
     */
    @PostMapping("/react/login")
    public Object login(@RequestBody User user){
        log.info("username:{},password:{}",user.getUsername(),user.getPassword());
        User userBean = userService.getUserByName(user.getUsername());
        Map<String,Object> map = new HashMap<String,Object>();

        if (userBean.getPassword().equals(user.getPassword())) {
            map.put("status","ok");
            map.put("type","account");
            map.put("currentAuthority","admin");
            return map;
        } else {
            map.put("status","error");
            map.put("type","account");
            map.put("currentAuthority","guest");
            return map;
        }

    }

}
