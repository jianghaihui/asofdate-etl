package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.RoleDao;
import com.asofdate.platform.model.RoleModel;
import com.asofdate.platform.service.RoleService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<RoleModel> findAll(String domainId) {
        return roleDao.findAll(domainId);
    }

    @Override
    public RoleModel getDetails(String roleId) {
        return roleDao.getDetails(roleId);
    }

    @Override
    public int add(RoleModel roleModel) {
        return roleDao.add(roleModel);
    }

    @Override
    public int delete(JSONArray jsonArray) {
        return roleDao.delete(jsonArray);
    }

    @Override
    public int update(RoleModel roleModel) {
        return roleDao.update(roleModel);
    }
}
