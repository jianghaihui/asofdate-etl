package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.model.GroupDefineModel;
import com.asofdate.dispatch.model.GroupTaskModel;
import com.asofdate.dispatch.service.GroupDefineService;
import com.asofdate.dispatch.service.GroupTaskService;
import com.asofdate.dispatch.service.TaskDependencyService;
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
import java.util.UUID;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@RestController
@RequestMapping(value = "/v1/dispatch/group/define")
public class GroupDefineController {
    private final Logger logger = LoggerFactory.getLogger(GroupDefineController.class);
    @Autowired
    private GroupDefineService groupDefineService;
    @Autowired
    private GroupTaskService groupTaskService;
    @Autowired
    private TaskDependencyService taskDependencyService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<GroupDefineModel> getAll(HttpServletRequest request) {
        String domainID = JwtService.getConnectUser(request).get("DomainId").toString();
        return groupDefineService.findAll(domainID);
    }

    /*
    * 新增任务组
    * */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletRequest request) {
        if (1 != groupDefineService.add(parse(request))) {
            return Hret.error(500, "新增任务组信息失败,任务组编码重复", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    /*
    * 删除任务组
    * */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request) {
        List<GroupDefineModel> args = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(request.getParameter("JSON"));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            GroupDefineModel groupDefineModel = new GroupDefineModel();
            groupDefineModel.setGroupId(jsonObject.getString("group_id"));
            groupDefineModel.setDomainId(jsonObject.getString("domain_id"));
            args.add(groupDefineModel);
        }
        String msg = groupDefineService.delete(args);
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
        if (1 != groupDefineService.update(parse(request))) {
            return Hret.error(500, "新增任务组信息失败,任务组编码重复", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    @ResponseBody
    public String getTask(HttpServletRequest request) {
        String groupId = request.getParameter("group_id");
        return groupTaskService.getTask(groupId).toString();
    }

    @RequestMapping(value = "/task/dependency", method = RequestMethod.GET)
    @ResponseBody
    public List<GroupTaskModel> getTaskDependency(HttpServletRequest request) {
        String id = request.getParameter("id");
        return taskDependencyService.getTaskDependency(id);
    }

    /*
    * 查询任务组可以配置的上级依赖任务
    * 禁止将任务组现有的下级任务配置成他的上级任务组依赖
    * @param group_id 任务组编码
    * @return JSON 可以选择的任务组列表
    * */
    @RequestMapping(value = "/group/task/current", method = RequestMethod.GET)
    @ResponseBody
    public List<GroupTaskModel> getGroupTasks(HttpServletRequest request) {
        String groupId = request.getParameter("group_id");
        String id = request.getParameter("id");
        logger.debug("group_id is ：{},id is:{}", groupId, id);
        return taskDependencyService.getGroupTask(groupId,id);
    }

    @RequestMapping(value = "/group/task/dependency", method = RequestMethod.POST)
    @ResponseBody
    public String addGroupTask(HttpServletResponse response, HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray(request.getParameter("JSON"));
        if (1 != taskDependencyService.addTaskDependency(jsonArray)) {
            response.setStatus(421);
            return Hret.error(421, "添加任务依赖失败", JSONObject.NULL);
        }
        return Hret.success(200, "新增依赖成功", JSONObject.NULL);
    }

    @RequestMapping(value = "/group/task/dependency/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteTaskDependency(HttpServletResponse response, HttpServletRequest request) {
        String uuid = request.getParameter("uuid");
        if (1 != taskDependencyService.deleteTaskDependency(uuid)) {
            response.setStatus(421);
            return Hret.error(421, "删除任务依赖失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/task/argument", method = RequestMethod.GET)
    @ResponseBody
    public String getGroupTaskArgument(HttpServletRequest request) {
        String id = request.getParameter("id");
        return groupTaskService.getTaskArg(id).toString();
    }

    @RequestMapping(value = "/task/argument/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateArgValue(HttpServletRequest request) {
        String argValue = request.getParameter("arg_value");
        String uuid = request.getParameter("uuid");
        String argId = request.getParameter("arg_id");

        logger.info("uuid is:" + uuid + ",arg value is:" + argValue);
        if (1 != groupDefineService.updateArg(argValue, uuid, argId)) {
            return Hret.error(421, "修改任务组参数失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    /*
    * 删除任务组中已经配置的任务信息
    * @param id 是任务组中,任务的唯一id
    * */
    @RequestMapping(value = "/task/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteTask(HttpServletResponse response, HttpServletRequest request) {
        String id = request.getParameter("id");
        if (1 != groupTaskService.deleteTask(id)) {
            response.setStatus(421);
            return Hret.error(500, "删除任务组中的任务信息失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/task/list/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteTaskList(HttpServletResponse response, HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray(request.getParameter("JSON"));
        List<String> args = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            args.add(jsonObject.getString("id"));
            logger.info("delete job id is :{}", jsonObject.getString("id"));
        }
        try {
            int size = groupTaskService.deleteTask(args);
            if (1 != size) {
                response.setStatus(421);
                return Hret.error(421, "删除任务组中的任务信息失败,任务已经被其他任务引用为上级依赖任务,请先删除依赖关系", JSONObject.NULL);
            }
            return Hret.success(200, "success", JSONObject.NULL);
        } catch (Exception e) {
            logger.info("删除任务组中的任务信息失败,任务已经被其他任务引用为上级依赖任务,请先删除依赖关系");
            response.setStatus(421);
            return Hret.error(421, "删除任务组中的任务信息失败,任务已经被其他任务引用为上级依赖任务,请先删除依赖关系", JSONObject.NULL);
        }
    }

    @RequestMapping(value = "/task/add", method = RequestMethod.POST)
    @ResponseBody
    public String addTask(HttpServletResponse response, HttpServletRequest request) {
        String group_id = request.getParameter("group_id");
        String task_id = request.getParameter("task_id");
        String domain_id = request.getParameter("domain_id");
        String arg_list = request.getParameter("arg_list");
        String id = UUID.randomUUID().toString();

        if (1 != groupTaskService.addTask(id, group_id, task_id, domain_id)) {
            response.setStatus(421);
            return Hret.error(421, "添加任务失败", JSONObject.NULL);
        }

        if (!"[]".equals(arg_list)) {
            JSONArray jsonArray = new JSONArray(arg_list);
            JSONArray arg = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                jsonObject.getString("arg_id");
                jsonObject.getString("arg_value");
                jsonObject.put("id", id);
                jsonObject.put("domain_id", domain_id);
                arg.put(jsonObject);
            }
            if (1 != groupTaskService.addGroupArg(arg)) {
                return Hret.error(421, "任务组添加参数失败", JSONObject.NULL);
            }
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    private GroupDefineModel parse(HttpServletRequest request) {
        String userId = JwtService.getConnectUser(request).get("UserId").toString();
        GroupDefineModel groupDefineModel = new GroupDefineModel();
        String groupId = JoinCode.join(request.getParameter("domain_id"), request.getParameter("group_id"));
        groupDefineModel.setGroupId(groupId);
        groupDefineModel.setCodeNumber(request.getParameter("group_id"));
        groupDefineModel.setCreateUser(userId);
        groupDefineModel.setModifyUser(userId);
        groupDefineModel.setDomainId(request.getParameter("domain_id"));
        groupDefineModel.setGroupDesc(request.getParameter("group_desc"));
        return groupDefineModel;
    }
}
