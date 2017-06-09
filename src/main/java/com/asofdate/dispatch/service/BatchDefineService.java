package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.BatchDefineModel;
import org.json.JSONArray;

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

    int setStatus(String batchId, int status);

    int updateAsofdate(String asofdate, String batchId);

    // 根据批次号,查询这个批次中的批次参数信息
    JSONArray getBatchArg(String batchId);

    // 根据批次号,向这个批次中,给批次参数赋值
    int addBatchArg(JSONArray jsonArray);
}
