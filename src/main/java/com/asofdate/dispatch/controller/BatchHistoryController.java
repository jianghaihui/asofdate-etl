package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.service.BatchHistoryService;
import com.asofdate.platform.authentication.JwtService;
import com.asofdate.utils.Hret;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/16.
 */
@RestController
@RequestMapping(value = "/v1/dispatch/history")
public class BatchHistoryController {
    @Autowired
    private BatchHistoryService batchHistoryService;

    @RequestMapping(method = RequestMethod.GET)
    public List findAll(HttpServletRequest request){
        String domainId = request.getParameter("domain_id");
        if (domainId == null || domainId.isEmpty()){
            domainId = JwtService.getConnectUser(request).getString("DomainId");
        }
        return batchHistoryService.findAll(domainId);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public String deleteHistory(HttpServletResponse response,HttpServletRequest request){
        String JSON = request.getParameter("JSON");
        JSONArray jsonArray = new JSONArray(JSON);
        int size = batchHistoryService.delete(jsonArray);
        if (size == 1){
            return Hret.success(200,"success", JSONObject.NULL);
        }
        response.setStatus(421);
        return Hret.error(421,"删除批次历史信息失败",JSONObject.NULL);
    }
}
