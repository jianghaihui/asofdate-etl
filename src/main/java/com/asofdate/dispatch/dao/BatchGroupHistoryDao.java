package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.BatchGroupHistoryModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
public interface BatchGroupHistoryDao {
    List<BatchGroupHistoryModel> findAll(String uuid);
}
