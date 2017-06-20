package com.asofdate.platform.controller;

import com.asofdate.platform.authentication.JwtService;
import com.asofdate.platform.model.OrgModel;
import com.asofdate.platform.service.OrgService;
import com.asofdate.utils.Hret;
import com.asofdate.utils.JoinCode;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@RestController
@RequestMapping(value = "/v1/auth/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    @RequestMapping(method = RequestMethod.GET)
    public List findAll(HttpServletRequest request) {
        String domainId = request.getParameter("domain_id");
        if (domainId == null || domainId.isEmpty()) {
            domainId = JwtService.getConnectUser(request).getString("DomainId");
        }
        return orgService.findAll(domainId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/sub")
    public List findSub(HttpServletResponse response, HttpServletRequest request) {
        String orgUnitId = request.getParameter("org_unit_id");
        String domainId = request.getParameter("domain_id");
        return orgService.findSub(domainId, orgUnitId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String add(HttpServletResponse response, HttpServletRequest request) {
        OrgModel orgModel = parse(request);
        int size = orgService.add(orgModel);
        if (size == 1) {
            return Hret.success(200, "success", null);
        }
        response.setStatus(421);
        return Hret.error(421, "新增机构信息失败，机构编码重复", null);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(HttpServletResponse response, HttpServletRequest request) {
        OrgModel orgModel = parse(request);
        int size = orgService.update(orgModel);
        if (size == 1) {
            return Hret.success(200, "success", null);
        }
        response.setStatus(421);
        return Hret.error(421, "更新机构信息失败，请联系管理员", null);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(HttpServletResponse response, HttpServletRequest request) {
        String JSON = request.getParameter("JSON");
        JSONArray jsonArray = new JSONArray(JSON);
        try {
            int size = orgService.delete(jsonArray);
            if (size == 1) {
                return Hret.success(200, "success", null);
            }
            response.setStatus(422);
            return Hret.error(422, "机构信息不存在，没有机构被删除", null);
        } catch (Exception e) {
            response.setStatus(421);
            return Hret.error(421, "机构已经被引用，请先解除引用关系，再来删除机构", null);
        }
    }

    private OrgModel parse(HttpServletRequest request) {
        OrgModel orgModel = new OrgModel();
        String codeNumber = request.getParameter("Org_unit_id");
        String domainId = request.getParameter("Domain_id");
        orgModel.setCode_number(codeNumber);
        orgModel.setOrg_desc(request.getParameter("Org_unit_desc"));
        orgModel.setDomain_id(domainId);
        orgModel.setUp_org_id(request.getParameter("Up_org_id"));
        String orgUnitId = JoinCode.join(domainId, codeNumber);
        orgModel.setOrg_id(orgUnitId);
        String userId = JwtService.getConnectUser(request).getString("UserId");
        orgModel.setCreate_user(userId);
        orgModel.setModify_user(userId);
        return orgModel;
    }
}
