package com.asofdate.dispatch.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public class ArgumentDefineModel {
    @NotEmpty(message = "参数不能为空")
    public String arg_id;
    @NotNull
    public String arg_type;
    public String arg_type_desc;
    public String arg_value;
    public String code_number;
    public String create_user;
    public String create_date;
    public String modify_user;
    public String modify_date;
    public String domain_id;
    public String arg_desc;
    public String bind_as_of_date;

    public String getBindAsOfDate() {
        return bind_as_of_date;
    }

    public void setBindAsOfDate(String bind_as_of_date) {
        this.bind_as_of_date = bind_as_of_date;
    }

    public String getArgDesc() {
        return arg_desc;
    }

    public void setArgDesc(String arg_desc) {
        this.arg_desc = arg_desc;
    }

    public String getArgId() {
        return arg_id;
    }

    public void setArgId(String arg_id) {
        this.arg_id = arg_id;
    }

    public String getArgType() {
        return arg_type;
    }

    public void setArgType(String arg_type) {
        this.arg_type = arg_type;
    }

    public String getArgTypeDesc() {
        return arg_type_desc;
    }

    public void setArgTypeDesc(String arg_type_desc) {
        this.arg_type_desc = arg_type_desc;
    }

    public String getArgValue() {
        return arg_value;
    }

    public void setArgValue(String arg_value) {
        this.arg_value = arg_value;
    }

    public String getCodeNumber() {
        return code_number;
    }

    public void setCodeNumber(String code_number) {
        this.code_number = code_number;
    }

    public String getCreateUser() {
        return create_user;
    }

    public void setCreateUser(String create_user) {
        this.create_user = create_user;
    }

    public String getCreateDate() {
        return create_date;
    }

    public void setCreateDate(String create_date) {
        this.create_date = create_date;
    }

    public String getModifyUser() {
        return modify_user;
    }

    public void setModifyUser(String modify_user) {
        this.modify_user = modify_user;
    }

    public String getModifyDate() {
        return modify_date;
    }

    public void setModifyDate(String modify_date) {
        this.modify_date = modify_date;
    }

    public String getDomainId() {
        return domain_id;
    }

    public void setDomainId(String domain_id) {
        this.domain_id = domain_id;
    }

    @Override
    public String toString() {
        return "ArgumentDefineModel(arg_id:" + this.arg_id + ",arg_desc:" + this.arg_desc + ",arg_value:" + this.arg_value + ")";
    }
}
