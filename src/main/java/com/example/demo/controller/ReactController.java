package com.example.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class ReactController {

    @GetMapping("/rule")
    public Object getAllUsers(
            @RequestParam( name = "current", required = false, defaultValue = "1")
                    int current,
            @RequestParam(name= "pageSize",required = false,defaultValue ="10" )
                    int pageSize
    ){
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

    @GetMapping("/currentUser")
    public Object currentUser(){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("name","Serati Ma");
        resultMap.put("avatar","https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png");
        resultMap.put("userid","00000001");
        resultMap.put("notifyCount",12);
        return  resultMap;
    }
}
