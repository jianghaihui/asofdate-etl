package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.ShareDomainDao;
import com.asofdate.platform.model.ShareDomainModel;
import com.asofdate.platform.service.ShareDomainService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/19.
 */
@Service
public class ShareDomainServiceImpl implements ShareDomainService {
    @Autowired
    private ShareDomainDao shareDomainDao;

    @Override
    public List<ShareDomainModel> findAll(String domainId) {
        return shareDomainDao.findAll(domainId);
    }

    @Override
    public List<ShareDomainModel> unShareTarget(String domainId) {
        return shareDomainDao.unShareTarget(domainId);
    }

    @Override
    public int add(ShareDomainModel shareDomainModel) {
        return shareDomainDao.add(shareDomainModel);
    }

    @Override
    public int delete(JSONArray jsonArray) {
        return shareDomainDao.delete(jsonArray);
    }

    @Override
    public int update(ShareDomainModel shareDomainModel) {
        return shareDomainDao.update(shareDomainModel);
    }
}
