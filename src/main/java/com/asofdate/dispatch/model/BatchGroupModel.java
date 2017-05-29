package com.asofdate.dispatch.model;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public class BatchGroupModel {
    public String uuid;
    public String batch_id;
    public String group_id;
    //    public String group_up_id;
    public String domain_id;

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

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

//    public String getGroup_up_id() {
//        return group_up_id;
//    }
//
//    public void setGroup_up_id(String group_up_id) {
//        this.group_up_id = group_up_id;
//    }

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }
}
