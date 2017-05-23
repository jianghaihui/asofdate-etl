package com.asofdate.controller;

import com.asofdate.etl.QuartzConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by hzwy23 on 2017/5/23.
 */
@Controller
public class DispatchController {

    @Autowired
    public QuartzConfiguration quartzConfiguration;

    @RequestMapping(value = "/v1/dispatch/start")
    @ResponseBody
    public String start() throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = quartzConfiguration.schedulerFactoryBean();
        schedulerFactoryBean.start();
        return "start dispatch";
    }
}
