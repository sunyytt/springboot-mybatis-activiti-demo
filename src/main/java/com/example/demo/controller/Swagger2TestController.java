package com.example.demo.controller;

import com.example.demo.common.result.ResultPager;
import com.example.demo.model.User;
import com.example.demo.service.user.UserService;
import com.example.demo.utils.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Api：修饰整个类，描述Controller的作用
 * @ApiOperation：描述一个类的一个方法，或者说一个接口
 * @ApiParam：单个参数描述
 * @ApiModel：用对象来接收参数
 * @ApiProperty：用对象接收参数时，描述对象的一个字段
 * @ApiResponse：HTTP响应其中1个描述
 * @ApiResponses：HTTP响应整体描述
 * @ApiIgnore：使用该注解忽略这个API
 * @ApiError ：发生错误返回的信息
 * @ApiImplicitParam：一个请求参数
 * @ApiImplicitParams：多个请求参数
 * 启动Spring Boot程序，访问：http://localhost:8087/swagger-ui.html
 */
@RestController
@RequestMapping("/swger")
public class Swagger2TestController {
    @Autowired
    private UserService userService;

    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少", required = false, dataType = "Integer")
    })
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

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(paramType = "path",name = "id", value = "用户ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public Object getUserById(@PathVariable Integer id){
        User user = userService.getUserById(id);
        return ResponseResult.SuccessResult(user);
    }
}
