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

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<TaskDefineModel> getAll(HttpServletRequest request) {
        String domainId = JwtService.getConnectUser(request).get("DomainId").toString();
        return taskDefineService.getAll(domainId);
    }


    /*
    * 新增任务组
    * */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletRequest request) {
        if (1 != taskDefineService.add(parse(request))) {
            return Hret.error(500, "新增任务信息失败,任务组编码重复", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    /*
    * 删除任务组
    * */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request) {
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
        return Hret.error(500, msg, JSONObject.NULL);
    }


    /*
    * 更新任务组
    * */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String update(HttpServletRequest request) {
        if (1 != taskDefineService.update(parse(request))) {
            return Hret.error(500, "新增任务组信息失败,任务组编码重复", JSONObject.NULL);
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
    public String updateSort(HttpServletResponse response,HttpServletRequest request) {
        String sortId = request.getParameter("sort_id");
        String uuid = request.getParameter("uuid");
        logger.info("uuid is:" + sortId);
        if ( 1 != taskDefineService.updateArgumentSort(sortId,uuid)){
            return Hret.error(421,"更新参数序号信息失败",JSONObject.NULL);
        }
        return Hret.success(200,"success",JSONObject.NULL);
    }

    @RequestMapping(value = "/argument/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteArg(HttpServletResponse response,HttpServletRequest request) {
        String uuid = request.getParameter("uuid");
        if ( 1 != taskDefineService.deleteArg(uuid)){
            return Hret.error(421,"删除参数信息失败",JSONObject.NULL);
        }
        return Hret.success(200,"success",JSONObject.NULL);
    }

    @RequestMapping(value = "/argument/type", method = RequestMethod.GET)
    @ResponseBody
    public String getArgType(HttpServletResponse response,HttpServletRequest request) {
        String argId = request.getParameter("arg_id");
        return taskDefineService.getArgType(argId).toString();
    }

    @RequestMapping(value = "/argument/value", method = RequestMethod.POST)
    @ResponseBody
    public String updateArgValue(HttpServletResponse response,HttpServletRequest request) {
        String argValue = request.getParameter("arg_value");
        String uuid = request.getParameter("uuid");
        if (1 != taskDefineService.updateArgValue(argValue,uuid)){
            return Hret.error(500,"更新任务参数信息值失败,请联系管理员",JSONObject.NULL);
        }
        return Hret.success(200,"success",JSONObject.NULL);
    }


    @RequestMapping(value = "/argument/add",method = RequestMethod.POST)
    @ResponseBody
    public String addArg(HttpServletResponse response,HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("task_id",request.getParameter("task_id"));
        jsonObject.put("arg_id",request.getParameter("arg_id"));
        jsonObject.put("domain_id",request.getParameter("domain_id"));
        jsonObject.put("arg_value",request.getParameter("arg_value"));
        jsonObject.put("sort_id",request.getParameter("sort_id"));
        if (1 != taskDefineService.addArg(jsonObject)){
            return Hret.error(500,"新增任务参数失败",JSONObject.NULL);
        }
        return Hret.success(200,"success",JSONObject.NULL);
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