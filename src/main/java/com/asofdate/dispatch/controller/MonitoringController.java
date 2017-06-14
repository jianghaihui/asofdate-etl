package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.service.BatchDefineService;
import com.asofdate.platform.authentication.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/14.
 */
@RestController
@RequestMapping(value = "/v1/dispatch/monitoring")
public class MonitoringController {
    private final Logger logger = LoggerFactory.getLogger(MonitoringController.class);

    @Autowired
    private BatchDefineService batchDefineService;

    @RequestMapping(method = RequestMethod.GET)
    public List getRunning(HttpServletResponse response, HttpServletRequest request) {
        String domainId = request.getParameter("domain_id");
        if (domainId == null || domainId.isEmpty()) {
            domainId = JwtService.getConnectUser(request).getString("DomainId");
        }
        return batchDefineService.getRunning(domainId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/radio")
    public String getBatchCompletedRadio(HttpServletRequest request) {
        String batchId = request.getParameter("batch_id");
        float radio = batchDefineService.getBatchCompletedRadio(batchId);
        return String.valueOf(radio);
    }
}
