package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.BatchDefineModel;
import com.asofdate.dispatch.model.GroupDefineModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public interface BatchDefineDao {
    List findAll(String domainId);
    int add(BatchDefineModel m);
    String delete(List<BatchDefineModel> m);
    int update(BatchDefineModel m);
    int getStatus(String batchId);
}
