package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.BatchJobStatusModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
public interface BatchJobRunningService {
    List<BatchJobStatusModel> findAll(String batchId, String gid);

    BatchJobStatusModel getDetails(String batchId, String gid, String tid);
}
