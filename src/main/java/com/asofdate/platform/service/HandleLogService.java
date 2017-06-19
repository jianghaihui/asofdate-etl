package com.asofdate.platform.service;

import com.asofdate.platform.model.HandleLogModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
public interface HandleLogService {
    List<HandleLogModel> findAll(String domainId);

    List<HandleLogModel> findAll(String domainId, Integer offset, Integer limit);

    Integer getTotal(String domainId);
}
