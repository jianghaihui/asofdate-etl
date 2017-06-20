package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.RoleResourceDao;
import com.asofdate.platform.model.ResourceModel;
import com.asofdate.platform.model.RoleResourceModel;
import com.asofdate.platform.service.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/20.
 */
@Service
public class RoleResourceServiceImpl implements RoleResourceService {
    @Autowired
    private RoleResourceDao roleResourceDao;

    @Override
    public List<RoleResourceModel> findAll(String roleId) {
        return roleResourceDao.findAll(roleId);
    }

    @Override
    public List<ResourceModel> getOther(String roleId) {
        return roleResourceDao.getOther(roleId);
    }

    @Override
    public int auth(String roleId, String resId) {
        return roleResourceDao.auth(roleId, resId);
    }

    @Override
    public int revoke(String roleId, String resId) {
        return roleResourceDao.revoke(roleId, resId);
    }
}
