package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.BatchGroupModel;
import com.asofdate.dispatch.model.GroupDependencyModel;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public interface BatchGroupDao {
    List findAll(String domainId);

    List<BatchGroupModel> getGroup(String batchId);

    int addGroup(JSONArray jsonArray);

    int deleteGroup(JSONArray jsonArray);

    List<BatchGroupModel> getDependency(String batchid, String id);
}
