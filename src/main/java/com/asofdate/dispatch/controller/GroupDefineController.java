package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.model.GroupDefineModel;
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
import java.util.ArrayList;
import java.util.List;

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
            groupDefineModel.setGroup_id(jsonObject.getString("group_id"));
            groupDefineModel.setDomain_id(jsonObject.getString("domain_id"));
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
    public String getTaskDependency(HttpServletRequest request) {
        String id = request.getParameter("id");
        JSONArray jsonArray = taskDependencyService.getTaskDependency(id);
        logger.info(jsonArray.toString());
        return jsonArray.toString();
    }

    @RequestMapping(value = "/task/argument", method = RequestMethod.GET)
    @ResponseBody
    public String getGroupTaskArgument(HttpServletRequest request) {
        String id = request.getParameter("id");
        return groupTaskService.getTaskArg(id).toString();
    }

    private GroupDefineModel parse(HttpServletRequest request) {
        String userId = JwtService.getConnectUser(request).get("UserId").toString();
        GroupDefineModel groupDefineModel = new GroupDefineModel();
        String groupId = JoinCode.join(request.getParameter("domain_id"), request.getParameter("group_id"));
        groupDefineModel.setGroup_id(groupId);
        groupDefineModel.setCode_number(request.getParameter("group_id"));
        groupDefineModel.setCreate_user(userId);
        groupDefineModel.setModify_user(userId);
        groupDefineModel.setDomain_id(request.getParameter("domain_id"));
        groupDefineModel.setGroup_desc(request.getParameter("group_desc"));
        return groupDefineModel;
    }
}
