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

    public String getBatch_status_desc() {
        return batch_status_desc;
    }

    public void setBatch_status_desc(String batch_status_desc) {
        this.batch_status_desc = batch_status_desc;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getCode_number() {
        return code_number;
    }

    public void setCode_number(String code_number) {
        this.code_number = code_number;
    }

    public String getBatch_desc() {
        return batch_desc;
    }

    public void setBatch_desc(String batch_desc) {
        this.batch_desc = batch_desc;
    }

    public String getBatch_status() {
        return batch_status;
    }

    public void setBatch_status(String batch_status) {
        this.batch_status = batch_status;
    }

    public String getAs_of_date() {
        return as_of_date;
    }

    public void setAs_of_date(String as_of_date) {
        this.as_of_date = as_of_date;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getModify_date() {
        return modify_date;
    }

    public void setModify_date(String modify_date) {
        this.modify_date = modify_date;
    }

    public String getModify_user() {
        return modify_user;
    }

    public void setModify_user(String modify_user) {
        this.modify_user = modify_user;
    }

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }
}
