package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.model.TaskDefineModel;
import com.asofdate.dispatch.service.TaskDefineService;
import com.asofdate.platform.authentication.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@RestController
@RequestMapping(value = "/v1/dispatch/task/define")
public class TaskDefineController {
    @Autowired
    private TaskDefineService taskDefineService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<TaskDefineModel> getAll(HttpServletRequest request) {
        String domainId = JwtService.getConnectUser(request).get("DomainId").toString();
        return taskDefineService.getAll(domainId);
    }
}