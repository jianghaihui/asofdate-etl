package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.model.BatchJobStatusModel;
import com.asofdate.dispatch.service.BatchJobHistoryService;
import com.asofdate.dispatch.service.BatchJobRunningService;
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
@RequestMapping(value = "/v1/dispatch/batch/job/running")
public class BatchJobRunningController {
    @Autowired
    private BatchJobRunningService batchJobRunningService;

    @RequestMapping(method = RequestMethod.GET)
    public List getJob(HttpServletRequest request){
        String batchId = request.getParameter("batch_id");
        String gid = request.getParameter("gid");
        return batchJobRunningService.findAll(batchId,gid);
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public BatchJobStatusModel getDetails(HttpServletRequest request){
        String batchId = request.getParameter("batch_id");
        String gid = request.getParameter("gid");
        String tid = request.getParameter("tid");
        return batchJobRunningService.getDetails(batchId,gid,tid);
    }
}
