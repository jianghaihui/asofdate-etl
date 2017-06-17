package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.BatchJobHistoryModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
public interface BatchJobHistoryDao {
    List<BatchJobHistoryModel> findAll(String uuid, String gid);
}
