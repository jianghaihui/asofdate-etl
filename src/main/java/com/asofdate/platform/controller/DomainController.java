package com.asofdate.platform.controller;

import com.asofdate.platform.authentication.JwtService;
import com.asofdate.platform.model.DomainModel;
import com.asofdate.platform.service.DomainService;
import com.asofdate.utils.Hret;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by hzwy23 on 2017/5/19.
 */
@RestController
@RequestMapping(value = "/v1/auth/domain")
public class DomainController {
    private final Logger logger = LoggerFactory.getLogger(DomainController.class);
    @Autowired
    private DomainService domainService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List getAll(){
        return domainService.getAll();
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletResponse response, HttpServletRequest request){
        JSONArray jsonArray = new JSONArray(request.getParameter("JSON"));
        String ret = domainService.delete(jsonArray);
        if ( "success".equals(ret) ){
            return Hret.success(200,"success",JSONObject.NULL);
        }
        response.setStatus(421);
        return Hret.error(421,"这个域已经被引用,无法被删除,请先删除域下边的机构信息,用户信息",ret);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletResponse response, HttpServletRequest request){
        DomainModel domainModel = parse(request);
        int size = domainService.add(domainModel);
        if (size == 1){
            return Hret.success(200,"success",JSONObject.NULL);
        }
        response.setStatus(421);
        return Hret.error(421,"域编码冲突,这个域已经存在,无法重复添加",JSONObject.NULL);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String update(HttpServletResponse response, HttpServletRequest request){
        DomainModel domainModel = parse(request);
        try {
            int size = domainService.update(domainModel);
            if (size == 1){
                return Hret.success(200,"success",JSONObject.NULL);
            }
            response.setStatus(422);
            return Hret.error(422,"没有对象被更新,域不存在",JSONObject.NULL);
        } catch (Exception e){
            response.setStatus(421);
            return Hret.error(421,"更新域信息失败,请联系管理员",JSONObject.NULL);
        }
    }

    private DomainModel parse(HttpServletRequest request){
        DomainModel domainModel = new DomainModel();
        domainModel.setDomain_id(request.getParameter("domain_id"));
        domainModel.setDomain_desc(request.getParameter("domain_desc"));
        domainModel.setDomain_status_cd(request.getParameter("domain_status_cd"));
        domainModel.setDomain_status_id(request.getParameter("domain_status_id"));
        String userId = JwtService.getConnectUser(request).getString("UserId");
        domainModel.setDomain_modify_user(userId);
        domainModel.setCreate_user_id(userId);
        return domainModel;
    }
}
