package com.asofdate.dispatch.model;

/**
 * Created by hzwy23 on 2017/6/14.
 */
public class BatchJobStatusModel {
    public String batchId;
    public String jobId;
    public String status;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
