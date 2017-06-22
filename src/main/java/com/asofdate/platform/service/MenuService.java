package com.asofdate.platform.service;

import com.asofdate.platform.model.MenuModel;
import com.asofdate.platform.model.ThemeValueModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
public interface MenuService {
    List<MenuModel> findAll();

    MenuModel getDetails(String resId);

    String update(String resId, String resDesc, String resUpId);

    ThemeValueModel getThemeDetails(String themeId, String resId);

    String add(ThemeValueModel themeValueModel);

    String delete(String resId);

    String updateTheme(ThemeValueModel themeValueModel);
}
