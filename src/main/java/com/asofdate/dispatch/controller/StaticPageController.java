package com.asofdate.dispatch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@Controller
public class StaticPageController {
    @RequestMapping(value = "/v1/dispatch/argument/page", method = RequestMethod.GET)
    public String getPage() {
        return "dispatch/argument_define";
    }

    @RequestMapping(value = "/v1/dispatch/batch/page", method = RequestMethod.GET)
    public String getBatchDefinePage() {
        return "dispatch/batch_define";
    }

    @RequestMapping(value = "/v1/dispatch/group/page", method = RequestMethod.GET)
    public String getGroupDefinePage() {
        return "dispatch/group_define";
    }

    @RequestMapping(value = "/v1/dispatch/task/page", method = RequestMethod.GET)
    public String getTaskDefinePage() {
        return "dispatch/task_define";
    }
}
