package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.TaskArgumentModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/25.
 */
public interface TaskArgumentService {
    List<TaskArgumentModel> findByBatchId(String domainId, String batchId);
}
