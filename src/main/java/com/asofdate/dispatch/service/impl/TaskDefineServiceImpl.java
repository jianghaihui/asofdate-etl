package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.TaskDefineDao;
import com.asofdate.dispatch.service.TaskDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
@Service
public class TaskDefineServiceImpl implements TaskDefineService {
    @Autowired
    public TaskDefineDao dispatchTaskDefineDao;

    @Override
    public List findAll(String domainId) {
        return dispatchTaskDefineDao.findAll(domainId);
    }
}
