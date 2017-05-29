package com.asofdate.dispatch.model;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public class GroupTaskModel {
    public String uuid;
    public String group_id;
    public String task_id;
    //    public String task_up_id;
    public String domain_id;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

//    public String getTask_up_id() {
//        return task_up_id;
//    }
//
//    public void setTask_up_id(String task_up_id) {
//        this.task_up_id = task_up_id;
//    }

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }
}
