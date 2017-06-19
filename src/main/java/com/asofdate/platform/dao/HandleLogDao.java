package com.asofdate.platform.dao;

import com.asofdate.platform.model.HandleLogModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
public interface HandleLogDao {
    List<HandleLogModel> findAll(String domainId);

    List<HandleLogModel> findAll(String domainId, Integer offset, Integer limit);

    Integer getTotal(String domainId);
}
