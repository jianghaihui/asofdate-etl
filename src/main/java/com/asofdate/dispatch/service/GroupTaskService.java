package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.GroupTaskModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/25.
 */
public interface GroupTaskService {
    List<GroupTaskModel> findByBatchId(String domainId, String batchId);
}