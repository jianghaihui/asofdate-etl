package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.BatchArgumentDao;
import com.asofdate.dispatch.dao.BatchDefineDao;
import com.asofdate.dispatch.dao.BatchJobStatusDao;
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

    @Autowired
    private BatchJobStatusDao batchJobStatusDao;

    @Override
    public List<BatchDefineModel> findAll(String domainId) {
        return batchDefineDao.findAll(domainId);
    }

    @Override
    public List<BatchDefineModel> getRunning(String domainId) {
        return batchDefineDao.getRunning(domainId);
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
    public int runBatchInit(String batchId) {
        return batchDefineDao.runBatchInit(batchId);
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

    @Override
    public float getBatchCompletedRadio(String batchId) {
        int completedCnt = batchJobStatusDao.getCompletedCnt(batchId);
        int totalCnt = batchJobStatusDao.getTotalCnt(batchId);
        if (totalCnt == 0) {
            return 0;
        }
        return (float) completedCnt / (float) totalCnt;

    }

    @Override
    public int destoryBatch(String batchId, String retMsg, int status) {
        return batchDefineDao.destoryBatch(batchId,retMsg,status);
    }

    @Override
    public int saveHistory(String batchId) {
        return batchDefineDao.saveHistory(batchId);
    }
}
