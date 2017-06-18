package com.asofdate.platform.controller;

import com.asofdate.platform.authentication.JwtService;
import com.asofdate.platform.model.RoleModel;
import com.asofdate.platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@RestController
@RequestMapping(value = "/v1/auth/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public List<RoleModel> findAll(HttpServletRequest request){
        String domainId = request.getParameter("domain_id");
        if (domainId == null || domainId.isEmpty()){
            domainId = JwtService.getConnectUser(request).getString("DomainId");
        }
        return roleService.findAll(domainId);
    }
}
