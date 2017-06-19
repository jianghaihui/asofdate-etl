package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.BatchGroupRunningDao;
import com.asofdate.dispatch.model.BatchGroupStatusModel;
import com.asofdate.dispatch.service.BatchGroupRunningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
@Service
public class BatchGroupRunningServiceImpl implements BatchGroupRunningService {
    @Autowired
    private BatchGroupRunningDao batchGroupRunningDao;

    @Override
    public List<BatchGroupStatusModel> findAll(String batchId) {
        return batchGroupRunningDao.findAll(batchId);
    }

    @Override
    public Integer getRatio(String batchId, String gid) {
        return batchGroupRunningDao.getRatio(batchId, gid);
    }

    @Override
    public BatchGroupStatusModel getDetails(String batchId, String gid) {
        return batchGroupRunningDao.getDetails(batchId, gid);
    }
}
