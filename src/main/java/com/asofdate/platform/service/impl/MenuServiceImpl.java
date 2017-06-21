package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.MenuDao;
import com.asofdate.platform.model.MenuModel;
import com.asofdate.platform.model.ThemeValueModel;
import com.asofdate.platform.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<MenuModel> findAll() {
        return menuDao.findAll();
    }

    @Override
    public MenuModel getDetails(String resId) {
        return menuDao.getDetails(resId);
    }

    @Override
    public String update(String resId, String resDesc,String resUpId) {
        return menuDao.update(resId,resDesc,resUpId);
    }

    @Override
    public ThemeValueModel getThemeDetails(String themeId, String resId) {
        return menuDao.getThemeDetails(themeId,resId);
    }

    @Override
    public String add(ThemeValueModel themeValueModel) {
        return menuDao.add(themeValueModel);
    }

    @Override
    public String delete(String resId) {
        return menuDao.delete(resId);
    }

    @Override
    public String updateTheme(ThemeValueModel themeValueModel) {
        return menuDao.updateTheme(themeValueModel);
    }
}
