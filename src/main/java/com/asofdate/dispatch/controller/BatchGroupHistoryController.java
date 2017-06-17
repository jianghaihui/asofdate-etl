package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.service.BatchGroupHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
@RestController
@RequestMapping(value = "/v1/dispatch/batch/group/history")
public class BatchGroupHistoryController {
    @Autowired
    private BatchGroupHistoryService batchGroupHistoryService;

    @RequestMapping(method = RequestMethod.GET)
    public List findAll(HttpServletRequest request) {
        String uuid = request.getParameter("uuid");
        return batchGroupHistoryService.findAll(uuid);
    }
}
