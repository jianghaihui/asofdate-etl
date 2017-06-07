package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.TaskDependencyModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/27.
 */
public interface TaskDependencyDao {
    List<TaskDependencyModel> findAll(String domainId);

    List<TaskDependencyModel> findById(String domainId, String batchId);

    JSONArray getTaskDependency(String id);

    JSONArray getGroupTasks(String groupId);

    int addTaskDependency(JSONArray jsonArray);

    int deleteTaskDependency(String uuid);
}
