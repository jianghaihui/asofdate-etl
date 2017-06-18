package com.asofdate.platform.controller;

import com.asofdate.platform.authentication.JwtService;
import com.asofdate.platform.model.HandleLogModel;
import com.asofdate.platform.service.HandleLogService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@RestController
@RequestMapping(value = "/v1/auth/handle/logs")
public class HandleLogController {
    @Autowired
    private HandleLogService handleLogService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAll(HttpServletRequest request){
        String domainId = JwtService.getConnectUser(request).getString("DomainId");
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");
        List<HandleLogModel> list = handleLogService.findAll(domainId,Integer.parseInt(offset),Integer.parseInt(limit));
        JSONObject jsonObject = new JSONObject();
        Integer total = handleLogService.getTotal(domainId);
        jsonObject.put("total",total);
        jsonObject.put("rows",list);
        return jsonObject.toString();
    }
}
