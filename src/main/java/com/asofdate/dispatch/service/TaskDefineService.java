package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.TaskDefineModel;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public interface TaskDefineService {
    List<TaskDefineModel> findAll(String domainId, String batchId);

    List<TaskDefineModel> getAll(String domainId);

    int add(TaskDefineModel m);

    String delete(List<TaskDefineModel> m);

    int update(TaskDefineModel m);

    JSONArray getTaskArg(String taskId);
}
