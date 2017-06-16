package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.BatchHistoryDao;
import com.asofdate.dispatch.model.BatchHistoryModel;
import com.asofdate.dispatch.service.BatchHistoryService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/16.
 */
@Service
public class BatchHistoryServiceImpl implements BatchHistoryService {
    @Autowired
    private BatchHistoryDao batchHistoryDao;

    @Override
    public List<BatchHistoryModel> findAll(String domainId) {
        return batchHistoryDao.findAll(domainId);
    }

    @Override
    public int delete(JSONArray jsonArray) {
        return batchHistoryDao.delete(jsonArray);
    }
}
