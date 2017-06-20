package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.OrgDao;
import com.asofdate.platform.model.OrgModel;
import com.asofdate.platform.service.OrgService;
import org.json.JSONArray;
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

    @Override
    public List<OrgModel> findSub(String domainId, String orgId) {
        return orgDao.findSub(domainId, orgId);
    }

    @Override
    public int add(OrgModel orgModel) {
        return orgDao.add(orgModel);
    }

    @Override
    public int delete(JSONArray jsonArray) {
        return orgDao.delete(jsonArray);
    }

    @Override
    public int update(OrgModel orgModel) {
        return orgDao.update(orgModel);
    }
}
