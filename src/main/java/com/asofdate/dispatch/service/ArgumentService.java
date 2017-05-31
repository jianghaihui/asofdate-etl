package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.TaskArgumentModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/28.
 */
public interface ArgumentService {
    List<TaskArgumentModel> getArgument(String uid);

    void afterPropertySet(String domainId, String batchId);
}
