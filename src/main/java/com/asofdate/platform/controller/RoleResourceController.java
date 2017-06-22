package com.asofdate.platform.controller;

import com.asofdate.platform.service.RoleResourceService;
import com.asofdate.utils.Hret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/20.
 */
@RestController
@RequestMapping(value = "/v1/auth/role/resource")
public class RoleResourceController {
    @Autowired
    private RoleResourceService roleResourceService;

    @RequestMapping(value = "/owner", method = RequestMethod.GET)
    public List getAll(HttpServletRequest request) {
        String roleId = request.getParameter("role_id");
        return roleResourceService.findAll(roleId);
    }

    @RequestMapping(value = "/other", method = RequestMethod.GET)
    public List getOther(HttpServletRequest request) {
        String roleId = request.getParameter("role_id");
        return roleResourceService.getOther(roleId);
    }

    @RequestMapping(value = "/revoke", method = RequestMethod.POST)
    public String revoke(HttpServletResponse response, HttpServletRequest request) {
        String roleId = request.getParameter("role_id");
        String resId = request.getParameter("res_id");
        try {
            int size = roleResourceService.revoke(roleId, resId);
            if (1 == size) {
                return Hret.success(200, "success", null);
            }
            response.setStatus(421);
            return Hret.error(421, "移除角色拥有的权限失败", null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setStatus(422);
            return Hret.error(422, "移除角色拥有的权限失败", null);
        }
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String auth(HttpServletResponse response, HttpServletRequest request) {
        String roleId = request.getParameter("role_id");
        String resId = request.getParameter("res_id");
        try {
            int size = roleResourceService.auth(roleId, resId);
            if (1 == size) {
                return Hret.success(200, "success", null);
            }
            response.setStatus(421);
            return Hret.error(421, "授权失败,请联系管理员", null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setStatus(422);
            return Hret.error(422, "授权失败,请联系管理员", null);
        }
    }
}
