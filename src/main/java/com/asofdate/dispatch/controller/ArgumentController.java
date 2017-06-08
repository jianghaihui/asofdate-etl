package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.model.ArgumentDefineModel;
import com.asofdate.dispatch.service.ArgumentService;
import com.asofdate.platform.authentication.JwtService;
import com.asofdate.utils.Hret;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@RequestMapping(value = "/v1/dispatch/argument/define")
@RestController
public class ArgumentController {
    @Autowired
    private ArgumentService argumentService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ArgumentDefineModel> getArgumentDefine(HttpServletRequest request) {
        Object domain_id = JwtService.getConnectUser(request).get("DomainId");
        List<ArgumentDefineModel> list = argumentService.findAll(domain_id.toString());
        return list;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String post(HttpServletRequest request) {

        ArgumentDefineModel argumentDefineModel = parse(request);
        if (1 == argumentService.add(argumentDefineModel)) {
            return Hret.success(200, "success", JSONObject.NULL);
        } else {
            return Hret.error(500, "插入参数信息失败", JSONObject.NULL);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    @ResponseBody
    public String delete(HttpServletRequest request) {

        List<ArgumentDefineModel> args = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(request.getParameter("JSON"));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            ArgumentDefineModel argumentDefineModel = new ArgumentDefineModel();
            argumentDefineModel.setArg_id(jsonObject.getString("arg_id"));
            argumentDefineModel.setDomain_id(jsonObject.getString("domain_id"));
            args.add(argumentDefineModel);
        }

        if ("success".equals(argumentService.delete(args))) {
            return Hret.success(200, "success", JSONObject.NULL);
        }
        return Hret.error(500, "删除参数失败,参数已经被引用,请先删除引用后,再来删除参数定义信息", JSONObject.NULL);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String put(HttpServletRequest request) {
        ArgumentDefineModel argumentDefineModel = parse(request);
        if (1 == argumentService.update(argumentDefineModel)) {
            return Hret.success(200, "success", JSONObject.NULL);
        } else {
            return Hret.error(500, "更新参数信息失败", JSONObject.NULL);
        }
    }

    private ArgumentDefineModel parse(HttpServletRequest request) {
        ArgumentDefineModel argumentDefineModel = new ArgumentDefineModel();
        argumentDefineModel.setArg_id(request.getParameter("arg_id"));
        argumentDefineModel.setArg_value(request.getParameter("arg_value"));
        argumentDefineModel.setArg_type(request.getParameter("arg_type"));
        argumentDefineModel.setArg_desc(request.getParameter("arg_desc"));
        argumentDefineModel.setDomain_id(request.getParameter("domain_id"));
        argumentDefineModel.setBind_as_of_date(request.getParameter("bind_as_of_date"));
        String userId = JwtService.getConnectUser(request).get("UserId").toString();
        argumentDefineModel.setCreate_user(userId);
        argumentDefineModel.setModify_user(userId);
        return argumentDefineModel;
    }

}
