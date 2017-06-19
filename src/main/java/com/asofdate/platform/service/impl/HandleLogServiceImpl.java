package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.HandleLogDao;
import com.asofdate.platform.model.HandleLogModel;
import com.asofdate.platform.service.HandleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Service
public class HandleLogServiceImpl implements HandleLogService {
    @Autowired
    private HandleLogDao handleLogDao;

    @Override
    public List<HandleLogModel> findAll(String domainId) {
        return handleLogDao.findAll(domainId);
    }

    @Override
    public List<HandleLogModel> findAll(String domainId, Integer offset, Integer limit) {
        return handleLogDao.findAll(domainId, offset, limit);
    }

    @Override
    public Integer getTotal(String domainId) {
        return handleLogDao.getTotal(domainId);
    }
}
