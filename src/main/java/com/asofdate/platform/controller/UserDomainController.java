package com.asofdate.platform.controller;

import com.asofdate.platform.authentication.JwtService;
import com.asofdate.platform.service.DomainService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@RestController
public class UserDomainController {
    @Autowired
    private DomainService domainService;

    @RequestMapping(value = "/v1/auth/domain/self/owner", method = RequestMethod.GET)
    @ResponseBody
    public String getDomain(HttpServletRequest request) {

        // 获取连接用户账号
        JSONObject authentication = JwtService
                .getConnectUser(request);
        Object domainId = authentication.get("DomainId");

        return domainService.findAll(domainId.toString()).toString();
    }
}
