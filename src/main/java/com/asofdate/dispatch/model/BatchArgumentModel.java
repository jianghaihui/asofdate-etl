package com.asofdate.dispatch.model;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public class BatchArgumentModel {
    public String uuid;
    public String batch_id;
    public String arg_id;
    public String arg_value;
    public String domain_id;

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getArg_id() {
        return arg_id;
    }

    public void setArg_id(String arg_id) {
        this.arg_id = arg_id;
    }

    public String getArg_value() {
        return arg_value;
    }

    public void setArg_value(String arg_value) {
        this.arg_value = arg_value;
    }
}
