package com.example.demo.controller;

import com.example.demo.common.result.ResultCode;
import com.example.demo.model.User;
import com.example.demo.service.user.UserService;
import com.example.demo.utils.JWTUtil;
import com.example.demo.utils.ResponseResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 说明
 *
 * 程序逻辑:
 * 我们POST用户名与密码到/login进行登入，如果成功返回一个加密token，失败的话直接返回401错误。
 * 之后用户访问每一个需要权限的网址请求必须在【headers】中添加Authorization字段，例如Authorization: token，token为密钥。
 * 后台会进行token的校验，如果有误会直接返回401。
 *
 * Token加密说明:
 * 携带了username信息在token中。
 * 设定了过期时间。
 * 使用用户登入密码对token进行加密。
 *
 * Token校验流程:
 * 获得token中携带的username信息。
 * 进入数据库搜索这个用户，得到他的密码。
 * 使用用户的密码来检验token是否正确。
 /login  登入
 /article 所有人都可以访问，但是用户与游客看到的内容不同
 /require_auth  登入的用户才可以进行访问
 /require_role  admin的角色用户才可以登入
 /require_permission  拥有view和edit权限的用户才可以访问
 */
@RestController
public class JWTController {

    private static final Logger log = LoggerFactory.getLogger(JWTController.class);

    private UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Object login(@RequestParam("username") String username,
                      @RequestParam("password") String password) {
        User userBean = userService.getUserByName(username);
        //盐（用户名+随机数）
//        String salt = userBean.getSalt();
        //原密码
//        String encodedPassword = ShiroKit.md5(password, username + salt);

        if (userBean.getPassword().equals(password)) {
            return ResponseResult.SuccessResult("Authorization: "+JWTUtil.sign(username, password));
        } else {
            return ResponseResult.FailResult("登陆失败");
        }
    }

    @GetMapping("/article")
    public Object article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return ResponseResult.SuccessResult("You are already logged in");
        } else {
            return ResponseResult.SuccessResult("You are guest");
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public Object requireAuth() {
        return ResponseResult.SuccessResult("You are authenticated");
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public Object requireRole(){
        return ResponseResult.SuccessResult("You are visiting require_role");
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public Object requirePermission() {
        return ResponseResult.SuccessResult("You are visiting permission require edit,view");
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object unauthorized() {
        return ResponseResult.FailResult(ResultCode.UNAUTHORIZED,"Unauthorized");
    }
}
