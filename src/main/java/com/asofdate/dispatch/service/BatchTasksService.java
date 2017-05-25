package com.asofdate.dispatch.service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public interface BatchTasksService {
    List findAllTasks(String domainId, String batchId);
}
