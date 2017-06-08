package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.ArgumentDefineModel;
import com.asofdate.dispatch.model.TaskArgumentModel;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/28.
 */
public interface ArgumentService {
    List<TaskArgumentModel> getArgument(String uid);

    void afterPropertySet(String domainId, String batchId);

    List<ArgumentDefineModel> findAll(String domainID);

    int add(ArgumentDefineModel m);

    /*
    * 删除任务
    * */
    String delete(List<ArgumentDefineModel> m);

    /*
    * 更新任务
    * */
    int update(ArgumentDefineModel m);

    JSONArray getBatchArg(String batchId);

    int addBatchArg(JSONArray jsonArray);
}
