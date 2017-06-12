package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.model.TaskDefineModel;
import com.asofdate.dispatch.service.TaskDefineService;
import com.asofdate.platform.authentication.JwtService;
import com.asofdate.utils.Hret;
import com.asofdate.utils.JoinCode;
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
@RestController
@RequestMapping(value = "/v1/dispatch/task/define")
public class TaskDefineController {
    private final Logger logger = LoggerFactory.getLogger(TaskDefineController.class);
    @Autowired
    private TaskDefineService taskDefineService;

    /*
    * http GET /v1/dispatch/task/define
    * 查询指定域中的所有任务定义信息
    * 如果指定域为空,则返回请求用户所属域的任务定义信息
    * */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<TaskDefineModel> getAll(HttpServletRequest request) {
        String domainId = request.getParameter("domain_id");
        if (domainId == null || domainId.isEmpty()) {
            domainId = JwtService.getConnectUser(request).get("DomainId").toString();
        }
        return taskDefineService.getAll(domainId);
    }

    /*
    * 新增任务组
    * */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String add(@Validated TaskDefineModel taskDefineModel, BindingResult bindingResult,HttpServletResponse response, HttpServletRequest request) {
        if (bindingResult.hasErrors()){
            for (ObjectError m:bindingResult.getAllErrors()){
                response.setStatus(421);
                return Hret.error(421, m.getDefaultMessage(), JSONObject.NULL);
            }
        }

        int size = taskDefineService.add(parse(request));
        if (1 != size) {
            response.setStatus(421);
            return Hret.error(500, "新增任务信息失败,任务组编码重复", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    /*
    * 删除任务组
    * */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletResponse response,HttpServletRequest request) {
        List<TaskDefineModel> args = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(request.getParameter("JSON"));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            TaskDefineModel taskDefineModel = new TaskDefineModel();
            taskDefineModel.setTaskId(jsonObject.getString("taskId"));
            taskDefineModel.setDomainId(jsonObject.getString("domainId"));
            args.add(taskDefineModel);
        }
        String msg = taskDefineService.delete(args);
        if ("success".equals(msg)) {
            return Hret.success(200, "success", JSONObject.NULL);
        }
        response.setStatus(421);
        return Hret.error(421, msg, JSONObject.NULL);
    }


    /*
    * 更新任务组
    * */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String update(@Validated TaskDefineModel taskDefineModel, BindingResult bindingResult,HttpServletResponse response,HttpServletRequest request) {
        if (bindingResult.hasErrors()){
            for (ObjectError m:bindingResult.getAllErrors()){
                response.setStatus(421);
                return Hret.error(421, m.getDefaultMessage(), JSONObject.NULL);
            }
        }

        int size = taskDefineService.update(parse(request));
        if (1 != size) {
            response.setStatus(421);
            return Hret.error(500, "更新任务组信息失败,任务组编码重复", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/argument", method = RequestMethod.GET)
    @ResponseBody
    public String getTaskId(HttpServletRequest request) {
        String taskId = request.getParameter("task_id");
        logger.info("task id is:" + taskId);
        return taskDefineService.getTaskArg(taskId).toString();
    }

    @RequestMapping(value = "/argument/sort", method = RequestMethod.PUT)
    @ResponseBody
    public String updateSort(HttpServletResponse response, HttpServletRequest request) {
        String sortId = request.getParameter("sort_id");
        String uuid = request.getParameter("uuid");
        logger.info("sort is:{},uuid is:{}",sortId, uuid);
        int size = taskDefineService.updateArgumentSort(sortId, uuid);

        if (1 != size) {
            response.setStatus(421);
            return Hret.error(421, "更新参数序号信息失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/argument/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteArg(HttpServletResponse response, HttpServletRequest request) {
        String uuid = request.getParameter("uuid");
        int size = taskDefineService.deleteArg(uuid);
        if (1 != size) {
            response.setStatus(421);
            return Hret.error(421, "删除参数信息失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/argument/type", method = RequestMethod.GET)
    @ResponseBody
    public String getArgType(HttpServletResponse response, HttpServletRequest request) {
        String argId = request.getParameter("arg_id");
        return taskDefineService.getArgType(argId).toString();
    }

    /*
    * 更新任务参数的值，在任务定义过程中，只能更新参数类型为任务类型的参数
    *
    * */
    @RequestMapping(value = "/argument/value", method = RequestMethod.POST)
    @ResponseBody
    public String updateArgValue(HttpServletResponse response, HttpServletRequest request) {
        String argValue = request.getParameter("arg_value");
        String uuid = request.getParameter("uuid");
        if (argValue == null || argValue.isEmpty()){

        }
        int size = taskDefineService.updateArgValue(argValue, uuid);
        if (1 != size) {
            response.setStatus(421);
            return Hret.error(421, "更新任务参数信息值失败,请联系管理员", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }


    @RequestMapping(value = "/argument/add", method = RequestMethod.POST)
    @ResponseBody
    public String addArg(HttpServletResponse response, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String taskId = request.getParameter("task_id");
        if(taskId == null || taskId.isEmpty()){
            response.setStatus(421);
            return Hret.error(421,"任务参数为空,请联系管理员",JSONObject.NULL);
        }
        jsonObject.put("task_id", taskId);

        String argId = request.getParameter("arg_id");
        if (argId == null || argId.isEmpty()){
            response.setStatus(421);
            return Hret.error(421,"请选择参数",JSONObject.NULL);
        }
        jsonObject.put("arg_id", argId);

        String sortId = request.getParameter("sort_id");
        if (sortId == null || sortId.isEmpty()){
            response.setStatus(421);
            return Hret.error(421,"参数排序号不能为空",JSONObject.NULL);
        }
        jsonObject.put("sort_id",sortId);

        jsonObject.put("domain_id", request.getParameter("domain_id"));

        jsonObject.put("arg_value", request.getParameter("arg_value"));

        int size = taskDefineService.addArg(jsonObject);
        if (1 != size) {
            response.setStatus(421);
            return Hret.error(421, "新增任务参数失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    private TaskDefineModel parse(HttpServletRequest request) {
        String userId = JwtService.getConnectUser(request).get("UserId").toString();
        TaskDefineModel taskDefineModel = new TaskDefineModel();
        String taskId = JoinCode.join(request.getParameter("domainId"), request.getParameter("taskId"));
        taskDefineModel.setTaskId(taskId);
        taskDefineModel.setCodeNumber(request.getParameter("taskId"));
        taskDefineModel.setCreateUser(userId);
        taskDefineModel.setModifyUser(userId);
        taskDefineModel.setDomainId(request.getParameter("domainId"));
        taskDefineModel.setTaskDesc(request.getParameter("taskDesc"));
        taskDefineModel.setTaskType(request.getParameter("taskType"));
        taskDefineModel.setScriptFile(request.getParameter("scriptFile"));
        return taskDefineModel;
    }
}