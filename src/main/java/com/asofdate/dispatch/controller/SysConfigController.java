package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.service.SysConfigService;
import com.asofdate.platform.authentication.JwtService;
import com.asofdate.utils.Hret;
import com.asofdate.utils.JoinCode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "/v1/dispatch/config/user",method = RequestMethod.POST)
    public String updateConfigValue(HttpServletResponse response, HttpServletRequest request){
        String domainId = request.getParameter("domain_id");
        String configId = request.getParameter("config_id");
        String configValue = request.getParameter("config_value");

        int size = sysConfigService.setValue(domainId,configId,configValue);
        if (size != 1){
            response.setStatus(421);
            return Hret.error(421,"更新ETL调度系统核心参数失败", JSONObject.NULL);
        }
        return Hret.success(200,"success",JSONObject.NULL);
    }
}
