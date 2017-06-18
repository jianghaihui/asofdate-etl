package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.OrgDao;
import com.asofdate.platform.model.OrgModel;
import com.asofdate.platform.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Service
public class OrgServiceImpl implements OrgService {
    @Autowired
    private OrgDao orgDao;

    @Override
    public List<OrgModel> findAll(String domainId) {
        return orgDao.findAll(domainId);
    }
}
