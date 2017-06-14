package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.BatchTaskDao;

/**
 * Created by hzwy23 on 2017/6/14.
 */
public class BatchTaskDaoImpl implements BatchTaskDao {
    @Override
    public int init(String batchId) {
        return 0;
    }

    @Override
    public int setTaskStatus(String batchId, String jobId, String status) {
        return 0;
    }

    @Override
    public int getTaskStatus(String batchId, String jobId) {
        return 0;
    }
}
