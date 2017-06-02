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