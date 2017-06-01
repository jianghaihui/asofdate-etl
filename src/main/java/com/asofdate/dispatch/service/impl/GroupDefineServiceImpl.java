package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.GroupDefineDao;
import com.asofdate.dispatch.model.GroupDefineModel;
import com.asofdate.dispatch.service.GroupDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@Service
public class GroupDefineServiceImpl implements GroupDefineService {
    @Autowired
    private GroupDefineDao groupDefineDao;

    @Override
    public List<GroupDefineModel> findAll(String domainId) {
        return groupDefineDao.findAll(domainId);
    }

    @Override
    public int update(GroupDefineModel m) {
        return groupDefineDao.update(m);
    }

    @Override
    public String delete(List<GroupDefineModel> m) {
        return groupDefineDao.delete(m);
    }

    @Override
    public int add(GroupDefineModel m) {
        return groupDefineDao.add(m);
    }
}
