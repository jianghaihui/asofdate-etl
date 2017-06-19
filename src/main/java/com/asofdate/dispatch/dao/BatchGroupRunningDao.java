package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.BatchGroupStatusModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
public interface BatchGroupRunningDao {
    List<BatchGroupStatusModel> findAll(String domainId);

    Integer getRatio(String batchId, String gid);

    BatchGroupStatusModel getDetails(String batchId, String gid);
}
