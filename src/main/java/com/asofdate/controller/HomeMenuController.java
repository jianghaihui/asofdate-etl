package com.asofdate.controller;

import com.asofdate.authentication.JwtService;
import com.asofdate.model.HomeMenuModel;
import com.asofdate.service.HomeMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hzwy23 on 2017/5/15.
 */
@RestController
public class HomeMenuController {
    @Autowired
    public HomeMenuService homeMenuService;

    @RequestMapping(value = "/v1/auth/main/menu")
    @ResponseBody
    public List<HomeMenuModel> homeMenu(HttpServletRequest request) {
        String TypeId = request.getParameter("TypeId");
        String Id = request.getParameter("Id");

        // 获取连接用户账号
        Authentication authentication = JwtService
                .getAuthentication((HttpServletRequest) request);
        String username = authentication.getName();

        List<HomeMenuModel> homeMenusModel = homeMenuService.findAuthedMenus(username, TypeId, Id);
        return homeMenusModel;
    }
}
