package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.MenuDao;
import com.asofdate.platform.model.MenuModel;
import com.asofdate.platform.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Service
public class MenuServiceImpl  implements MenuService{
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<MenuModel> findAll() {
        return menuDao.findAll();
    }
}
