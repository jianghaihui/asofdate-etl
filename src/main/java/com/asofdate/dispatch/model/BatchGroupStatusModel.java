package com.asofdate.dispatch.model;

/**
 * Created by hzwy23 on 2017/6/15.
 */
public class BatchGroupStatusModel {
    public String batchId;
    public String gid;
    public String status;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
