package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.TaskDefineModel;
import org.json.JSONArray;
import org.json.JSONObject;

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

    int updateArgumentSort(String sortId, String uuid);

    int deleteArg(String uuid);

    JSONObject getArgType(String argId);

    int addArg(JSONObject jsonObject);

    int updateArgValue(String argValue, String uuid);
}
