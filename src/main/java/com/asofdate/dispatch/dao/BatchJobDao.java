package com.asofdate.dispatch.dao;

import java.util.Map;

/**
 * Created by hzwy23 on 2017/6/14.
 */
public interface BatchJobDao {
    int init(String batchId, Map<String, Integer> map);

    int setTaskStatus(String batchId, String jobId, int status);

    int getTaskStatus(String batchId, String jobId);

    int getCompletedCnt(String batchId);

    int getTotalCnt(String batchId);
}
