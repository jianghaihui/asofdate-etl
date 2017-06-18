package com.asofdate.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.Request;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Controller
public class StaticFileController {
    @RequestMapping(value = "/v1/help/system/help",method = RequestMethod.GET)
    public String getSysHelp(){
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
    public String getBatchPage(){
        return "hauth/sys_batch_page";
    }

    @RequestMapping(value = "/v1/auth/HandleLogsPage", method = RequestMethod.GET)
    public String getHandleLog(){
        return "hauth/handle_logs_page";
    }

    @RequestMapping(value = "/v1/auth/resource/page", method = RequestMethod.GET)
    public String getResourcePage(){
        return "hauth/res_info_page";
    }
}
