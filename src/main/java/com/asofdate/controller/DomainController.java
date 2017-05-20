package com.asofdate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by hzwy23 on 2017/5/19.
 */
@Controller
public class DomainController {
    @RequestMapping(value = "/v1/auth/domain/page",method = RequestMethod.GET)
    public String getPage(){
        return "hauth/domain_info";
    }
}
