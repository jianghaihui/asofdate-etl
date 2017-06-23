package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.BatchGroupModel;
import com.asofdate.dispatch.model.GroupDependencyModel;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/25.
 */
public interface BatchGroupService {
    List<BatchGroupModel> findByBatchId(String domainId, String batchId);

    List<BatchGroupModel> getGroup(String batchId);

    int addGroup(JSONArray jsonArray);

    int deleteGroup(JSONArray jsonArray);

    List<BatchGroupModel> getDependency(String batchid, String id);
}
