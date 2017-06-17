package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.model.BatchGroupStatusModel;
import com.asofdate.dispatch.service.BatchGroupRunningService;
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
@RequestMapping(value = "/v1/dispatch/batch/group/running")
public class BatchGroupRunningController {
    @Autowired
    private BatchGroupRunningService batchGroupRunningService;

    @RequestMapping(method = RequestMethod.GET)
    public List<BatchGroupStatusModel> findAll(HttpServletRequest request){
        String batchId = request.getParameter("batch_id");
        return batchGroupRunningService.findAll(batchId);
    }
}
