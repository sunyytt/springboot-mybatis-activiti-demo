package com.example.demo.common.jwt;

import org.apache.shiro.authc.AuthenticationToken;

public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    /**
     * principals：身份，即主体的标识属性，可以是任何东西，如用户名、邮箱等，唯一即可。
     * 一个主体可以有多个 principals，但只有一个 Primary principals，一般是用户名 / 密码 / 手机号。
     * @return
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * credentials：证明 / 凭证，即只有主体知道的安全值，如密码 / 数字证书等。
     * 最常见的 principals 和 credentials 组合就是用户名 / 密码了。接下来先进行一个基本的身份认证。
     * @return
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
