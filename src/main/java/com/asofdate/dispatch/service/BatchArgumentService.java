package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.BatchArgumentModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/25.
 */
public interface BatchArgumentService {
    List<BatchArgumentModel> findByBatchId(String domainId, String batchId);
}
