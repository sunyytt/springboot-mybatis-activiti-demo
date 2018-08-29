package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 其中，日期格式需要做转换，在需要日期转换的Controller中使用SpringMVC的注解@initbinder和Spring自带的WebDateBinder类来操作。
 * WebDataBinder是用来绑定请求参数到指定的属性编辑器.由于前台传到controller里的值是String类型的，
 * 当往Model里Set这个值的时候，如果set的这个属性是个对象，Spring就会去找到对应的editor进行转换，然后再SET进去。
 */

@RestController
@RequestMapping(value = "/test")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping(value = "/getUserList")
    public List<UserInfo> getUserList() {
        return userService.getUserList();
    }

    @GetMapping(value = "/getUserInfo")
    public UserInfo getUserInfoByName(@RequestParam("name") String name) {
        return userService.getUserByName(name);
    }

    @GetMapping(value = "/getCurrentUserList")
    public List<UserInfo> getCurrentUserList(){
        return userService.getCurrentUserList();
    }

    @GetMapping(value="/getPageUserList")
    public Page<UserInfo> getPageUserList(){
        return userService.getPageUserList();
    }

    @PutMapping(value = "/addUserInfo")
    public UserInfo addUserInfo(UserInfo userInfo) {
        return userService.addUserInfo(userInfo);
    }

    @PostMapping(value ="/updateUserInfo")
    public UserInfo updateUserInfo(UserInfo userInfo){
        return userService.updateUserInfoById(userInfo);
    }

    @PostMapping(value="/deleteUserInfo")
    public void deleteUserInfo(@RequestParam("id") Long id){
        userService.deleteUserInfoById(id);
    }

    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));/*TimeZone时区，解决差8小时的问题*/
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
