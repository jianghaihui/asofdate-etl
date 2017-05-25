package com.asofdate.dispatch.model;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public class TaskArgumentModel {
    public String uuid;
    public String task_id;
    public String arg_id;
    public String arg_value;
    public String domain_id;
    public String sort_id;

    public String getSort_id() {
        return sort_id;
    }

    public void setSort_id(String sort_id) {
        this.sort_id = sort_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
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

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }
}
