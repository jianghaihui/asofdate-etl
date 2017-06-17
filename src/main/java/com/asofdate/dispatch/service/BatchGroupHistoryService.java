package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.BatchGroupHistoryModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
public interface BatchGroupHistoryService {
    List<BatchGroupHistoryModel> findAll(String uuid);
}
