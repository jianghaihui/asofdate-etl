package com.asofdate.platform.controller;

import com.asofdate.platform.model.MenuModel;
import com.asofdate.platform.model.ThemeValueModel;
import com.asofdate.platform.service.MenuService;
import com.asofdate.utils.Hret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@RestController
@RequestMapping(value = "/v1/auth/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MenuModel> findAll(HttpServletRequest request) {
        return menuService.findAll();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(HttpServletResponse response,HttpServletRequest request){
        String resId = request.getParameter("res_id");
        String resDesc = request.getParameter("res_name");
        String resUpId = request.getParameter("res_up_id");

        String msg = menuService.update(resId,resDesc,resUpId);
        if ( "success".equals(msg) ) {
            return Hret.success(200,"success",null);
        }
        response.setStatus(421);
        return Hret.error(421,msg,null);
    }

    @RequestMapping( method = RequestMethod.POST)
    public String add(HttpServletResponse response,HttpServletRequest request){
        ThemeValueModel menuModel = parse(request);
        String msg = menuService.add(menuModel);
        if ("success".equals(msg)){
            return Hret.success(200,msg,null);
        }
        response.setStatus(421);
        return Hret.success(421,msg,null);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/delete")
    public String delete(HttpServletResponse response, HttpServletRequest request){
        String resId = request.getParameter("res_id");
        String msg = menuService.delete(resId);
        if ("success".equals(msg)){
            return Hret.success(200,msg,null);
        }
        response.setStatus(421);
        return Hret.error(421,msg,null);
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public MenuModel getDetails(HttpServletResponse httpServletResponse,HttpServletRequest request){
        String resId = request.getParameter("res_id");
        return menuService.getDetails(resId);
    }

    @RequestMapping(value = "/theme",method = RequestMethod.GET)
    public ThemeValueModel getThemeDetails(HttpServletResponse response,HttpServletRequest request){
        String resId = request.getParameter("res_id");
        String themeId = request.getParameter("theme_id");
        return menuService.getThemeDetails(themeId,resId);
    }

    @RequestMapping(value = "/theme/config",method = RequestMethod.POST)
    public String updateTheme(HttpServletResponse response,HttpServletRequest request){
        ThemeValueModel themeValueModel = parse(request);
        String msg = menuService.updateTheme(themeValueModel);
        if ("success".equals(msg)){
            return Hret.success(200,msg,null);
        }
        response.setStatus(422);
        return Hret.error(422,msg,null);
    }

    private ThemeValueModel parse(HttpServletRequest request){
        ThemeValueModel menuModel = new ThemeValueModel();
        String resId = request.getParameter("res_id");
        String resName = request.getParameter("res_name");
        String themeId = request.getParameter("theme_id");
        String resType = request.getParameter("res_type");
        String resUpId = request.getParameter("res_up_id");
        String resUrl = request.getParameter("res_url");
        String resImg = request.getParameter("res_img");
        String resBgColor = request.getParameter("res_bg_color");
        String resOpenType = request.getParameter("res_open_type");
        String resClass = request.getParameter("res_class");
        String groupId = request.getParameter("group_id");
        String sortId = request.getParameter("sort_id");
        String resAttr = "0";

        switch (resType){
            case "0":
                resUpId = "-1";
                break;
            case "1":
                resAttr = "1";
                break;
            case "2":
                resAttr = "1";
                sortId = "0";
                break;
            case "4":
                sortId = "0";
                break;
        }

        menuModel.setRes_id(resId);
        menuModel.setRes_name(resName);
        menuModel.setTheme_id(themeId);
        menuModel.setRes_type(resType);
        menuModel.setRes_up_id(resUpId);
        menuModel.setRes_open_type(resOpenType);
        menuModel.setRes_url(resUrl);
        menuModel.setRes_class(resClass);
        menuModel.setRes_img(resImg);
        menuModel.setRes_bg_color(resBgColor);
        menuModel.setRes_attr(resAttr);
        menuModel.setGroup_id(groupId);
        menuModel.setSort_id(sortId);

        return menuModel;
    }
}
