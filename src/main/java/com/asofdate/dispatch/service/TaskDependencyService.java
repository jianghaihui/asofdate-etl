package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.TaskDependencyModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Set;

/**
 * Created by hzwy23 on 2017/5/27.
 */
public interface TaskDependencyService {
    List<TaskDependencyModel> findById(String domainId, String batchId);

    Set<String> getTaskDependency(String gid, String id);

    void afterPropertiesSet(String domainId, String batchId);

    JSONArray getTaskDependency(String id);

    JSONArray getGroupTask(String groupId);

    int addTaskDependency(JSONObject jsonObject);

    int deleteTaskDependency(String uuid);
}
