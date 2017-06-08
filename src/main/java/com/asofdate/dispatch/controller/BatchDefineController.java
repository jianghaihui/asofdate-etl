package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.model.BatchDefineModel;
import com.asofdate.dispatch.model.BatchStatus;
import com.asofdate.dispatch.service.ArgumentService;
import com.asofdate.dispatch.service.BatchDefineService;
import com.asofdate.dispatch.service.BatchGroupService;
import com.asofdate.dispatch.service.GroupDependencyService;
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
@RequestMapping(value = "/v1/dispatch/batch/define")
public class BatchDefineController {

    private static Logger logger = LoggerFactory.getLogger(BatchDefineController.class);
    @Autowired
    private BatchDefineService batchDefineService;
    @Autowired
    private BatchGroupService batchGroupService;
    @Autowired
    private GroupDependencyService groupDependencyService;
    @Autowired
    private ArgumentService argumentService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<BatchDefineModel> getAll(HttpServletRequest request) {
        String domainId = JwtService.getConnectUser(request).get("DomainId").toString();
        return batchDefineService.findAll(domainId);
    }


    /*
    * 新增任务组
    * */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletRequest request) {
        if (1 != batchDefineService.add(parse(request))) {
            return Hret.error(500, "新增任务组信息失败,任务组编码重复", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    /*
    * 删除任务组
    * */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletResponse response, HttpServletRequest request) {
        List<BatchDefineModel> args = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(request.getParameter("JSON"));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String batchId = jsonObject.getString("batch_id");

            if (BatchStatus.BATCH_STATUS_RUNNING == batchDefineService.getStatus(batchId)) {
                response.setStatus(421);
                return Hret.error(500, "批次正在运行中,无法被删除", JSONObject.NULL);
            }
            BatchDefineModel batchDefineModel = new BatchDefineModel();
            batchDefineModel.setBatch_id(batchId);
            batchDefineModel.setDomain_id(jsonObject.getString("domain_id"));
            args.add(batchDefineModel);
        }
        String msg = batchDefineService.delete(args);
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
    public String update(HttpServletResponse response, HttpServletRequest request) {
        BatchDefineModel m = parse(request);
        if (batchDefineService.getStatus(m.getBatch_id()) == BatchStatus.BATCH_STATUS_RUNNING) {
            response.setStatus(421);
            return Hret.error(500, "批次正在运行中,无法编辑", JSONObject.NULL);
        }
        if (1 != batchDefineService.update(m)) {
            return Hret.error(500, "新增批次信息失败,批次编码重复", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    @ResponseBody
    public String getGroup(HttpServletRequest request) {
        String batchId = request.getParameter("batch_id");
        return batchGroupService.getGroup(batchId).toString();
    }

    @RequestMapping(value = "/stop",method = RequestMethod.POST)
    @ResponseBody
    public String stop(HttpServletRequest request){
        String batchId = request.getParameter("batch_id");
        if (1 != batchDefineService.setStatus(batchId,4)){
            return Hret.error(421,"停止批次失败",JSONObject.NULL);
        }
        return Hret.success(200,"停止批次成功",JSONObject.NULL);
    }

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    @ResponseBody
    public String addGroup(HttpServletRequest request) {
        String batchId = request.getParameter("batch_id");
        String domainId = request.getParameter("domain_id");
        String JSON = request.getParameter("JSON");
        JSONArray jsonArray = new JSONArray(JSON);
        JSONArray arg = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            jsonObject.put("domain_id", domainId);
            jsonObject.put("batch_id", batchId);
            arg.put(jsonObject);
        }
        if (1 != batchGroupService.addGroup(arg)) {
            return Hret.error(421, "添加任务组失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/group/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteGroup(HttpServletRequest request) {
        String id = request.getParameter("id");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonArray.put(jsonObject);
        if (1 != batchGroupService.deleteGroup(jsonArray)) {
            return Hret.error(421, "删除任务组失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }


    @RequestMapping(value = "/group/dependency", method = RequestMethod.GET)
    @ResponseBody
    public String getDependency(HttpServletRequest request) {
        String id = request.getParameter("id");
        return groupDependencyService.getUp(id).toString();
    }

    @RequestMapping(value = "/group/dependency", method = RequestMethod.POST)
    @ResponseBody
    public String addDependency(HttpServletResponse response, HttpServletRequest request) {

        String JSON = request.getParameter("JSON");
        JSONArray jsonArray = new JSONArray(JSON);

        if (1 != groupDependencyService.addGroupDependency(jsonArray)) {
            response.setStatus(421);
            return Hret.success(421, "新增任务依赖失败", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }

    @RequestMapping(value = "/group/dependency/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteGroupDependency(HttpServletResponse response, HttpServletRequest request) {
        String uuid = request.getParameter("uuid");
        if (1 != groupDependencyService.deleteGroupDependency(uuid)) {
            response.setStatus(421);
            return Hret.error(421, "删除任务组依赖关系失败,请联系管理员", JSONObject.NULL);
        }
        return Hret.success(200, "success", JSONObject.NULL);
    }


    @RequestMapping(value = "/argument", method = RequestMethod.GET)
    @ResponseBody
    public String getBatchArg(HttpServletRequest request) {
        String id = request.getParameter("batch_id");
        return argumentService.getBatchArg(id).toString();
    }

    @RequestMapping(value = "/argument", method = RequestMethod.POST)
    @ResponseBody
    public String addBatchArg(HttpServletRequest request){
        JSONArray jsonArray = new JSONArray(request.getParameter("JSON"));
        if (1 != argumentService.addBatchArg(jsonArray)){
            return Hret.error(421,"添加批次参数失败",JSONObject.NULL);
        }
        return Hret.success(200,"success",JSONObject.NULL);
    }

    @RequestMapping(value = "/asofdate",method = RequestMethod.PUT)
    @ResponseBody
    public String updateAsofdate(HttpServletResponse response,HttpServletRequest request){
        String batchId = request.getParameter("batch_id");
        String asofdate = request.getParameter("as_of_date");
        logger.info("batch id is :"+ batchId+",as of date is :" + asofdate);
        if (1 != batchDefineService.updateAsofdate(asofdate,batchId)){
            response.setStatus(421);
            return Hret.error(421,"更新批次日期失败",JSONObject.NULL);
        }

        return Hret.success(200,"success",JSONObject.NULL);
    }

    private BatchDefineModel parse(HttpServletRequest request) {
        String userId = JwtService.getConnectUser(request).get("UserId").toString();
        BatchDefineModel batchDefineModel = new BatchDefineModel();
        String batchId = JoinCode.join(request.getParameter("domain_id"), request.getParameter("batch_id"));
        batchDefineModel.setBatch_id(batchId);
        batchDefineModel.setCode_number(request.getParameter("batch_id"));
        batchDefineModel.setCreate_user(userId);
        batchDefineModel.setModify_user(userId);
        batchDefineModel.setDomain_id(request.getParameter("domain_id"));
        batchDefineModel.setBatch_desc(request.getParameter("batch_desc"));
        batchDefineModel.setBatch_status(request.getParameter("batch_status"));
        batchDefineModel.setAs_of_date(request.getParameter("as_of_date"));
        return batchDefineModel;
    }
}
