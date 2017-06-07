package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.GroupDependencyDao;
import com.asofdate.dispatch.model.GroupDependencyModel;
import com.asofdate.dispatch.service.GroupDependencyService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by hzwy23 on 2017/5/27.
 */
@Service
@Scope("prototype")
public class GroupDependencyServiceImpl implements GroupDependencyService {
    @Autowired
    private GroupDependencyDao groupDependencyDao;
    /*
    * key : 任务组
    * value: 所有依赖的任务组
    * */
    private Map<String, Set<GroupDependencyModel>> groupMap = new HashMap<>();

    @Override
    public List<GroupDependencyModel> findById(String domainId, String batchId) {
        return groupDependencyDao.findById(domainId, batchId);
    }

    /*
    * 初始化任务组的依赖关系
    * */
    @Override
    public void afterPropertiesSet(String domainId, String batchId) {
        /*
        * 初始化批次中任务组的依赖HashMap
        * */
        for (GroupDependencyModel m : findById(domainId, batchId)) {
            if (this.groupMap.containsKey(m.getId())) {
                this.groupMap.get(m.getId()).add(m);
            } else {
                Set<GroupDependencyModel> set = new HashSet<>();
                set.add(m);
                this.groupMap.put(m.getId(), set);
            }
        }
    }

    /*
    * 获取任务组的依赖任务组列表
    * @param String gid 表示任务组id
    * */
    @Override
    public Set<GroupDependencyModel> getGroupDependency(String gid) {
        return this.groupMap.get(gid);
    }

    @Override
    public JSONArray getUp(String id) {
        return groupDependencyDao.getGroupDependency(id);
    }

    @Override
    public int deleteGroupDependency(String uuid) {
        return groupDependencyDao.deleteGroupDependency(uuid);
    }

    @Override
    public int addGroupDependency(JSONArray jsonArray) {
        return groupDependencyDao.addGroupDependency(jsonArray);
    }
}
