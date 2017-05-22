package com.asofdate.controller;

import com.asofdate.utils.JSONResult;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.quartz.SchedulerException;
import org.quartz.impl.StdScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hzwy23 on 2017/5/16.
 */
@Controller
public class LoginController {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private final String HEADER_STRING = "Authorization";

    @RequestMapping(value = "/signout",produces = "application/json")
    @ResponseBody
    public void logout(HttpServletResponse response, HttpServletRequest request){
        Cookie[] cookie = request.getCookies();
        if (cookie != null){
            for(int i = 0; i < cookie.length;i++){
                if (cookie[i].getName().equals(HEADER_STRING)){
                    cookie[i].setMaxAge(0);
                    response.addCookie(cookie[i]);
                }
            }
        }
        try{
            response.getOutputStream().println(JSONResult.fillResultString(0, "", "logout successfully"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index() {
        schedulerFactoryBean.start();
        return "index";
    }
}
