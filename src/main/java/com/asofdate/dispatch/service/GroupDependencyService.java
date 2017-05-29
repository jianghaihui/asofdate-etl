package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.GroupDependencyModel;

import java.util.List;
import java.util.Set;

/**
 * Created by hzwy23 on 2017/5/27.
 */
public interface GroupDependencyService {
    List<GroupDependencyModel> findById(String domainId, String batchId);

    void afterPropertiesSet(String domainId, String batchId);

    Set<GroupDependencyModel> getGroupDependency(String gid);
}
