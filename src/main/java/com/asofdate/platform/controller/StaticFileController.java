package com.asofdate.platform.controller;

import com.asofdate.platform.model.DomainModel;
import com.asofdate.platform.model.RoleModel;
import com.asofdate.platform.service.AuthService;
import com.asofdate.platform.service.DomainService;
import com.asofdate.platform.service.RoleService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Controller
public class StaticFileController {
    @Autowired
    private DomainService domainService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/v1/help/system/help", method = RequestMethod.GET)
    public String getSysHelp() {
        return "help/auth_help";
    }

    @RequestMapping(value = "/v1/auth/domain/page", method = RequestMethod.GET)
    public String getPage() {
        return "hauth/domain_info";
    }

    @RequestMapping(value = "/v1/auth/user/page", method = RequestMethod.GET)
    public String getUserPage() {
        return "hauth/UserInfoPage";
    }

    @RequestMapping(value = "/v1/auth/resource/org/page", method = RequestMethod.GET)
    public String getOrgPage() {
        return "hauth/org_page";
    }

    @RequestMapping(value = "/v1/auth/role/page", method = RequestMethod.GET)
    public String getRolePage() {
        return "hauth/role_info_page";
    }

    @RequestMapping(value = "/v1/auth/batch/page", method = RequestMethod.GET)
    public String getBatchPage() {
        return "hauth/sys_batch_page";
    }

    @RequestMapping(value = "/v1/auth/HandleLogsPage", method = RequestMethod.GET)
    public String getHandleLog() {
        return "hauth/handle_logs_page";
    }

    @RequestMapping(value = "/v1/auth/resource/page", method = RequestMethod.GET)
    public String getResourcePage() {
        return "hauth/res_info_page";
    }


    @RequestMapping(value = "/v1/auth/domain/share/page", method = RequestMethod.GET)
    public String getDomainSharePage(HttpServletResponse response, HttpServletRequest request, Map<String, Object> map) {
        String domainId = request.getParameter("domain_id");

        JSONObject o = authService.domainAuth(request, domainId, "r");
        if (!o.getBoolean("status")) {
            response.setStatus(423);
            map.put("domainId", domainId);
            return "hauth/NoAuth";
        }
        DomainModel domainModel = domainService.getDomainDetails(domainId);

        map.put("domainId", domainModel.getDomain_id());
        map.put("domainDesc", domainModel.getDomain_desc());
        map.put("statusDesc", domainModel.getDomain_status_desc());
        map.put("createDate", domainModel.getMaintance_date());
        map.put("createUser", domainModel.getCreate_user_id());
        map.put("modifyDate", domainModel.getDomain_modify_date());
        map.put("modifyUser", domainModel.getDomain_modify_user());
        return "hauth/domain_share_info";
    }

    @RequestMapping(value = "/v1/auth/role/resource/details", method = RequestMethod.GET)
    public String getResRolePage(HttpServletResponse response, HttpServletRequest request, Map<String, Object> map) {
        String roleId = request.getParameter("role_id");
        RoleModel roleModel = roleService.getDetails(roleId);

        map.put("code_number", roleModel.getCode_number());
        map.put("role_id", roleModel.getRole_id());
        map.put("role_name", roleModel.getRole_name());
        map.put("domain_id", roleModel.getDomain_id());
        map.put("domain_desc", roleModel.getDomain_desc());

        return "hauth/res_role_rel_page";
    }

    @RequestMapping(value = "/v1/auth/swagger/page", method = RequestMethod.GET)
    public String getSwagger() {
        return "help/swagger_index";
    }
}
