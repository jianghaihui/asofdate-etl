package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.SysConfigModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/15.
 */
public interface SysConfigDao {
    List<SysConfigModel> findAll(String domainId);

    int setValue(String domainId, String configId, String configValue);

    String getValue(String domainId, String configId);
}
