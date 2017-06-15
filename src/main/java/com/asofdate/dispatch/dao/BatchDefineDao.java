package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.BatchDefineModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public interface BatchDefineDao {
    List findAll(String domainId);

    List<BatchDefineModel> getRunning(String domainId);

    int add(BatchDefineModel m);

    String delete(List<BatchDefineModel> m);

    int update(BatchDefineModel m);

    int getStatus(String batchId);

    int setStatus(String batchId, int status);

    int updateAsofdate(String asofdate, String batchId);

    int runBatchInit(String batchId);

    int destoryBatch(String batchId,String retMsg,int status);
}
