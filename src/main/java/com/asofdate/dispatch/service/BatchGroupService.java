package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.BatchGroupModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/25.
 */
public interface BatchGroupService {
    List<BatchGroupModel> findByBatchId(String domainId, String batchId);
}
