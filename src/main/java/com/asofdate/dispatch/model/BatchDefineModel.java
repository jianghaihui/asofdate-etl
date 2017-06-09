package com.asofdate.dispatch.model;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public class BatchDefineModel {
    public String batch_id;
    public String code_number;
    public String batch_desc;
    public String batch_status;
    public String as_of_date;
    public String create_date;
    public String create_user;
    public String modify_date;
    public String modify_user;
    public String domain_id;
    public String batch_status_desc;

    public String getBatchStatusDesc() {
        return batch_status_desc;
    }

    public void setBatchStatusDesc(String batch_status_desc) {
        this.batch_status_desc = batch_status_desc;
    }

    public String getBatchId() {
        return batch_id;
    }

    public void setBatchId(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getCodeNumber() {
        return code_number;
    }

    public void setCodeNumber(String code_number) {
        this.code_number = code_number;
    }

    public String getBatchDesc() {
        return batch_desc;
    }

    public void setBatchDesc(String batch_desc) {
        this.batch_desc = batch_desc;
    }

    public String getBatchStatus() {
        return batch_status;
    }

    public void setBatchStatus(String batch_status) {
        this.batch_status = batch_status;
    }

    public String getAsOfDate() {
        return as_of_date;
    }

    public void setAsOfDate(String as_of_date) {
        this.as_of_date = as_of_date;
    }

    public String getCreateDate() {
        return create_date;
    }

    public void setCreateDate(String create_date) {
        this.create_date = create_date;
    }

    public String getCreateUser() {
        return create_user;
    }

    public void setCreateUser(String create_user) {
        this.create_user = create_user;
    }

    public String getModifyDate() {
        return modify_date;
    }

    public void setModifyDate(String modify_date) {
        this.modify_date = modify_date;
    }

    public String getModifyUser() {
        return modify_user;
    }

    public void setModifyUser(String modify_user) {
        this.modify_user = modify_user;
    }

    public String getDomainId() {
        return domain_id;
    }

    public void setDomainId(String domain_id) {
        this.domain_id = domain_id;
    }
}
