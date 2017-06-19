package com.asofdate.platform.controller;

import com.asofdate.platform.authentication.JwtService;
import com.asofdate.platform.model.UserModel;
import com.asofdate.platform.service.AuthService;
import com.asofdate.platform.service.UserService;
import com.asofdate.utils.Hret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by hzwy23 on 2017/5/19.
 */
@RestController
@RequestMapping(value = "/v1/auth/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.GET)
    public List<UserModel> findAll(HttpServletRequest request) {
        String domainId = request.getParameter("domain_id");
        if (domainId == null || domainId.isEmpty()) {
            domainId = JwtService.getConnectUser(request).getString("DomainId");
        }
        return userService.findAll(domainId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public List<UserModel> search(HttpServletRequest request) {
        String domainId = request.getParameter("domain_id");
        String orgId = request.getParameter("org_id");
        String statusCd = request.getParameter("status_id");
        return userService.findAll(domainId, orgId, statusCd);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String add(HttpServletResponse response, HttpServletRequest request) {
        UserModel args = parse(request);
        if (args == null) {
            return Hret.error(422, "参数解析失败,请按照要求填写表单", null);
        }

        String domainId = args.getDomain_id();

        Boolean status = authService.domainAuth(request, domainId, "w").getBoolean("status");
        if (!status) {
            return Hret.error(422, "您没有权限在这个域中创建用户", null);
        }

        int size = userService.add(args);
        if (size != 1) {
            response.setStatus(421);
            return Hret.error(421, "新增用户失败,账号已存在", null);
        }
        return Hret.success(200, "success", null);
    }

    private UserModel parse(HttpServletRequest request) {
        UserModel userModel = new UserModel();

        return userModel;
    }
}
