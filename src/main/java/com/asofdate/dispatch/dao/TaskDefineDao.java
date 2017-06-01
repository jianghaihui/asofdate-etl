package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.TaskDefineModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public interface TaskDefineDao {
    List findAll(String domainId);
    int add(TaskDefineModel m);
    String delete(List<TaskDefineModel> m);
    int update(TaskDefineModel m);
}
