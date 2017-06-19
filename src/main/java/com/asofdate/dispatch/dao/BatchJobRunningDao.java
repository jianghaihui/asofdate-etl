package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.BatchJobStatusModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
public interface BatchJobRunningDao {
    List<BatchJobStatusModel> findAll(String batchId, String gid);

    BatchJobStatusModel getDetails(String batchId, String gid, String tid);
}
