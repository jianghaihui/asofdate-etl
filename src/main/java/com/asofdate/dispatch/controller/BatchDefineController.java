package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.model.BatchDefineModel;
import com.asofdate.dispatch.model.BatchStatus;
import com.asofdate.dispatch.service.BatchDefineService;
import com.asofdate.dispatch.service.BatchGroupService;
import com.asofdate.dispatch.service.GroupDependencyService;
import com.asofdate.platform.authentication.JwtService;
import com.asofdate.utils.Hret;
import com.asofdate.utils.JoinCode;
import org.json.JSONArray;
import org.json.JSONObject;
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
    @Autowired
    private BatchDefineService batchDefineService;
    @Autowired
    private BatchGroupService batchGroupService;
    @Autowired
    private GroupDependencyService groupDependencyService;

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

    @RequestMapping(value = "/group/dependency", method = RequestMethod.GET)
    @ResponseBody
    public String getDependency(HttpServletRequest request) {
        String id = request.getParameter("id");
        return groupDependencyService.getUp(id).toString();
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
