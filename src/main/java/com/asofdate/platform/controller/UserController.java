package com.asofdate.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by hzwy23 on 2017/5/19.
 */
@Controller
public class UserController {
    @RequestMapping(value = "/v1/auth/user/page", method = RequestMethod.GET)
    public String getPage() {
        return "hauth/UserInfoPage";
    }
}
