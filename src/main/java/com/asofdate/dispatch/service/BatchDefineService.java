package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.BatchDefineModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
public interface BatchDefineService {
    List<BatchDefineModel> findAll(String domainId);

    int add(BatchDefineModel m);

    String delete(List<BatchDefineModel> m);

    int update(BatchDefineModel m);

    int getStatus(String batchId);

    int setStatus(String batchId,int status);
}
