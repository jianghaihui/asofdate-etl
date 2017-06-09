package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.BatchArgumentDao;
import com.asofdate.dispatch.dao.BatchDefineDao;
import com.asofdate.dispatch.model.BatchDefineModel;
import com.asofdate.dispatch.service.BatchDefineService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@Service
public class BatchDefineServiceImpl implements BatchDefineService {
    @Autowired
    private BatchDefineDao batchDefineDao;

    @Autowired
    private BatchArgumentDao batchArgumentDao;

    @Override
    public List<BatchDefineModel> findAll(String domainId) {
        return batchDefineDao.findAll(domainId);
    }

    @Override
    public int add(BatchDefineModel m) {
        return batchDefineDao.add(m);
    }

    @Override
    public String delete(List<BatchDefineModel> m) {
        return batchDefineDao.delete(m);
    }

    @Override
    public int update(BatchDefineModel m) {
        return batchDefineDao.update(m);
    }

    @Override
    public int getStatus(String batchId) {
        return batchDefineDao.getStatus(batchId);
    }

    @Override
    public int setStatus(String batchId, int status) {
        return batchDefineDao.setStatus(batchId, status);
    }

    @Override
    public int updateAsofdate(String asofdate, String batchId) {
        return batchDefineDao.updateAsofdate(asofdate, batchId);
    }


    @Override
    public JSONArray getBatchArg(String batchId) {
        return batchArgumentDao.getBatchArg(batchId);
    }

    @Override
    public int addBatchArg(JSONArray jsonArray) {
        return batchArgumentDao.addBatchArg(jsonArray);
    }

}
