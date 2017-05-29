package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.TaskDependencyModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/27.
 */
public interface TaskDependencyDao {
    List<TaskDependencyModel> findAll(String domainId);

    List<TaskDependencyModel> findById(String domainId, String batchId);
}
