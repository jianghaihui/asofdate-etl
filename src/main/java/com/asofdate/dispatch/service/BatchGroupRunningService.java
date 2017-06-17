package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.BatchGroupStatusModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
public interface BatchGroupRunningService {
    List<BatchGroupStatusModel> findAll(String batchId);
}
