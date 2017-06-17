package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.BatchGroupHistoryDao;
import com.asofdate.dispatch.model.BatchGroupHistoryModel;
import com.asofdate.dispatch.service.BatchGroupHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
@Service
public class BatchGroupHistoryServiceImpl implements BatchGroupHistoryService {
    @Autowired
    private BatchGroupHistoryDao batchGroupHistoryDao;

    @Override
    public List<BatchGroupHistoryModel> findAll(String uuid) {
        return batchGroupHistoryDao.findAll(uuid);
    }
}
