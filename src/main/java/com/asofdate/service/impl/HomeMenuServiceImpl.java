package com.asofdate.service.impl;

import com.asofdate.dao.HomeMenuDao;
import com.asofdate.model.HomeMenuModel;
import com.asofdate.service.HomeMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/17.
 */
@Service
public class HomeMenuServiceImpl implements HomeMenuService {

    @Autowired
    public HomeMenuDao homeMenuDao;

    @Override
    public List<HomeMenuModel> findAuthedMenus(String userId,String typeId,String resId) {
        List<HomeMenuModel> list = homeMenuDao.findById(userId,typeId,resId);
        return list;
    }
}
