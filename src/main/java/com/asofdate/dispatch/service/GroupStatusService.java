package com.asofdate.dispatch.service;

import java.util.Map;

/**
 * Created by hzwy23 on 2017/5/28.
 */
public interface GroupStatusService {
    void afterPropertiesSet(String domainId, String batchId);

    Map getRunnableGroups();

    void setGroupRunning(String gid);

    void setGroupCompleted(String gid);

    void setGroupError(String gid);

    int getGroupStatus(String gid);

    boolean isGroupRunable(String gid);
}
