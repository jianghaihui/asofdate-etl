package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.model.ArgumentDefineModel;
import com.asofdate.dispatch.service.ArgumentService;
import com.asofdate.platform.authentication.JwtService;
import com.asofdate.utils.Hret;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@RequestMapping(value = "/v1/dispatch/argument/define")
@RestController
public class ArgumentController {
    private final Logger logger = LoggerFactory.getLogger(ArgumentController.class);
    @Autowired
    private ArgumentService argumentService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<ArgumentDefineModel> getArgumentDefine(HttpServletRequest request) {
        String domainId = request.getParameter("domain_id");
        if (domainId == null) {
            JSONObject jsonObject = JwtService.getConnectUser(request);
            domainId = jsonObject.getString("DomainId");
        }
        List<ArgumentDefineModel> list = argumentService.findAll(domainId);
        return list;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String postArgumentDefine(HttpServletResponse response, HttpServletRequest request) {
        ArgumentDefineModel argumentDefine = parse(request);

        if (argumentDefine.getArgId().isEmpty()){
            response.setStatus(421);
            return Hret.error(421,"参数编码必须由1-30位字母、数字组成",JSONObject.NULL);
        }

        if (argumentDefine.getArgDesc().isEmpty()){
            response.setStatus(421);
            return Hret.error(421,"请输入详细的参数描述信息",JSONObject.NULL);
        }

        if (argumentDefine.getArgType() == null){
            response.setStatus(421);
            return Hret.error(421,"请选择参数类型",JSONObject.NULL);
        }

        if ("1".equals(argumentDefine.getArgType()) && argumentDefine.getArgValue().isEmpty()){
            response.setStatus(421);
            return Hret.error(421,"请填写固定参数，参数值",JSONObject.NULL);
        }

        if ("4".equals(argumentDefine.getArgType()) && argumentDefine.getBindAsOfDate() == null){
            response.setStatus(421);
            return Hret.error(421,"批次类型参数，请选择是否与数据日期绑定",JSONObject.NULL);
        }

        try {
            int size = argumentService.add(argumentDefine);
            if (1 == size) {
                return Hret.success(200, "success", JSONObject.NULL);
            } else {
                response.setStatus(421);
                return Hret.error(421, "新增参数信息失败,参数已存在,无法重复添加", JSONObject.NULL);
            }
        } catch (Exception e) {
            response.setStatus(421);
            return Hret.error(421, "新增参数信息失败,参数已存在,无法重复添加", JSONObject.stringToValue(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    @ResponseBody
    public String deleteArgumentDefine(HttpServletResponse response, HttpServletRequest request) {
        List<ArgumentDefineModel> args = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(request.getParameter("JSON"));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            ArgumentDefineModel argumentDefineModel = new ArgumentDefineModel();
            argumentDefineModel.setArgId(jsonObject.getString("arg_id"));
            argumentDefineModel.setDomainId(jsonObject.getString("domain_id"));
            args.add(argumentDefineModel);
        }
        String msg = argumentService.delete(args);
        logger.info("删除参数信息,返回状态是:{}", msg);
        if ("success".equals(msg)) {
            return Hret.success(200, "success", JSONObject.NULL);
        }
        response.setStatus(421);
        return Hret.error(421, "删除参数失败,参数已经被引用,请先删除引用后,再来删除参数定义信息", JSONObject.stringToValue(msg));
    }

    /*
    * 更新参数定义信息
    * */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String putArgumentDefine(HttpServletResponse response, HttpServletRequest request) {
        ArgumentDefineModel argumentDefine = parse(request);

        if (argumentDefine.getArgId().isEmpty()){
            response.setStatus(421);
            return Hret.error(421,"参数编码必须由1-30位字母、数字组成",JSONObject.NULL);
        }

        if (argumentDefine.getArgDesc().isEmpty()){
            response.setStatus(421);
            return Hret.error(421,"请输入详细的参数描述信息",JSONObject.NULL);
        }

        if (argumentDefine.getArgType() == null){
            response.setStatus(421);
            return Hret.error(421,"请选择参数类型",JSONObject.NULL);
        }

        if ("1".equals(argumentDefine.getArgType()) && argumentDefine.getArgValue().isEmpty()){
            response.setStatus(421);
            return Hret.error(421,"请填写固定参数，参数值",JSONObject.NULL);
        }

        if ("4".equals(argumentDefine.getArgType()) && argumentDefine.getBindAsOfDate() == null){
            response.setStatus(421);
            return Hret.error(421,"批次类型参数，请选择是否与数据日期绑定",JSONObject.NULL);
        }

        try {
            int size = argumentService.update(argumentDefine);
            if (1 == size) {
                return Hret.success(200, "success", JSONObject.NULL);
            } else {
                response.setStatus(421);
                return Hret.error(421, "更新参数信息失败,请联系管理员", JSONObject.NULL);
            }
        } catch (Exception e) {
            response.setStatus(421);
            return Hret.error(421, "更新参数信息失败,请联系管理员", JSONObject.stringToValue(e.getMessage()));
        }
    }

    /*
    * 从客户单请求中,获取参数
    * 并转换成ArgumentDefineModel对象
    * */
    private ArgumentDefineModel parse(HttpServletRequest request) {
        ArgumentDefineModel argumentDefineModel = new ArgumentDefineModel();
        argumentDefineModel.setArgId(request.getParameter("arg_id"));
        argumentDefineModel.setArgValue(request.getParameter("arg_value"));
        argumentDefineModel.setArgType(request.getParameter("arg_type"));
        argumentDefineModel.setArgDesc(request.getParameter("arg_desc"));
        argumentDefineModel.setDomainId(request.getParameter("domain_id"));
        argumentDefineModel.setBindAsOfDate(request.getParameter("bind_as_of_date"));
        String userId = JwtService.getConnectUser(request).get("UserId").toString();
        argumentDefineModel.setCreateUser(userId);
        argumentDefineModel.setModifyUser(userId);
        return argumentDefineModel;
    }
}
