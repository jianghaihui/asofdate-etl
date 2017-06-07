package com.asofdate.dispatch.controller;

import com.asofdate.utils.JoinCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    @RequestMapping(value = "/v1/dispatch/groupAndTask/page", method = RequestMethod.GET)
    public String getGroupTaskPage(HttpServletRequest request, Map<String, Object> map) {
        map.put("groupId",request.getParameter("groupId"));
        map.put("groupDesc",request.getParameter("groupDesc"));
        return "dispatch/group_task";
    }

    @RequestMapping(value = "/v1/dispatch/batchAndGroup/page", method = RequestMethod.GET)
    public String getBatchGroupPage(HttpServletRequest request, Map<String, Object> map) {
        map.put("batchId",request.getParameter("batchId"));
        map.put("batchDesc",request.getParameter("batchDesc"));
        map.put("domainId",JoinCode.getFirst(request.getParameter("batchId")));
        return "dispatch/batch_group";
    }
}
