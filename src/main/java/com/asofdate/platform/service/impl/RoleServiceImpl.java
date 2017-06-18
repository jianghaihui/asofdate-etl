package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.RoleDao;
import com.asofdate.platform.model.RoleModel;
import com.asofdate.platform.service.RoleService;
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
}
