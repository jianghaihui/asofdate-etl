package com.asofdate.platform.authentication;

import com.asofdate.platform.model.UserLoginModel;
import com.asofdate.platform.service.LoginService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by hzwy23 on 2017/5/18.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    public LoginService loginService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        Object pd = authentication.getCredentials();
        if (pd == null) {
            return new UsernamePasswordAuthenticationToken(name, "", new ArrayList<>());
        }
        String password = pd.toString();
        UserLoginModel userLoginModel = loginService.loginValidator(name, password);
        // 认证逻辑
        if (userLoginModel.isFlag()) {
            // 这里设置权限和角色
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
            authorities.add(new GrantedAuthorityImpl("AUTH_WRITE"));
            authorities.add(new GrantedAuthorityImpl("ACTUATOR"));

            // 生成令牌
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
            return auth;
        } else {
            logger.info("登录失败,原因是:账号 {}: {}", userLoginModel.getUsername(), userLoginModel.getMessage());
            throw new BadCredentialsException(JSONObject.wrap(userLoginModel).toString());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
