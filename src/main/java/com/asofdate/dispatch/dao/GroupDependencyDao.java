package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.GroupDependencyModel;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/27.
 */
public interface GroupDependencyDao {
    List<GroupDependencyModel> findAll(String domainId);

    List<GroupDependencyModel> findById(String domainId, String batchId);

    JSONArray getGroupDependency(String id);
}
