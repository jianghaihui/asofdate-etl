package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.service.SysConfigService;
import com.asofdate.platform.authentication.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/15.
 */
@RestController
public class SysConfigController {
    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping(value = "/v1/dispatch/config/sys",method = RequestMethod.GET)
    public List getALL(HttpServletRequest request){
        String domainId = request.getParameter("domain_id");
        if (domainId == null || domainId.isEmpty()){
            domainId = JwtService.getConnectUser(request).getString("DomainId");
        }
        return sysConfigService.findAll(domainId);
    }
}
