package com.asofdate.dispatch.service;

import java.util.Map;

/**
 * Created by hzwy23 on 2017/5/28.
 */
public interface TaskStatusService {
    void afterPropertiesSet(String domainId, String batchId);

    boolean isGroupCompleted(String gid, String groupId);

    int getTaskStatus(String gid, String id);

    boolean isTaskRunnable(String gid, String id);

    void setTaskCompleted(String uid);

    void setTaskRunning(String uid);

    void setTaskError(String uid);

    Map getRunnableTasks(String gid, String groupId);

    boolean isBatchCompleted();

    boolean isError();

    Map<String, Integer> getAll();
}
