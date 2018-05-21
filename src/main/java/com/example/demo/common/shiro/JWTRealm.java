package com.example.demo.common.shiro;

import com.example.demo.common.jwt.JWTToken;
import com.example.demo.model.User;
import com.example.demo.service.user.UserService;
import com.example.demo.utils.JWTUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class JWTRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LogManager.getLogger(JWTRealm.class);

    private UserService userService;

    @Override
    public String getName() {
        return "my_realm";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     *  //判断此Realm是否支持此Token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持 JWTToken 类型的Token
        return token instanceof JWTToken;
    }

    /**
     *  此方法调用hasRole,hasPermission的时候才会进行回调.
     *    <p>
     *    权限信息.(授权):
     *   1、如果用户正常退出，缓存自动清空；
     *   2、如果用户非正常退出，缓存自动清空；
     *   3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     *  （需要手动编程进行实现；放在service进行调用）
     *  在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
     *  :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /*
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
         * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行。
         */
        String username = JWTUtil.getUsername(principals.toString());
        User user = userService.getUserByName(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole());
        Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);

        //角色表，权限，用户表分开时用
//        for (SysRole role : managerInfo.getRoles()) {
//            //设置角色
//            authorizationInfo.addRole(role.getRole());
//            for (Permission p : role.getPermissions()) {
//                //设置权限
//                authorizationInfo.addStringPermission(p.getPermission());
//            }
//        }
        return simpleAuthorizationInfo;
    }


    /**
     *  认证信息(身份验证)
     *   Authentication 是用来验证用户身份
     *   默认使用此方法进行用户名正确与否验证，
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);//得到用户名
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User userBean = userService.getUserByName(username);
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        //？密码怎么用数据库查得呢？ 这是校验token,不是登陆校验密码！！！！！
        if (! JWTUtil.verify(token, username, userBean.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
