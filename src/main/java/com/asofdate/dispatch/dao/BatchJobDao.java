package com.asofdate.dispatch.dao;

/**
 * Created by hzwy23 on 2017/6/14.
 */
public interface BatchTaskDao {
    int init(String batchId);
    int setTaskStatus(String batchId,String jobId,String status);
    int getTaskStatus(String batchId,String jobId);
}
