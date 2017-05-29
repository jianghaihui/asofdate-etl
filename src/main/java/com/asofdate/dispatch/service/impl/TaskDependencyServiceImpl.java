package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.TaskDependencyDao;
import com.asofdate.dispatch.model.BatchGroupModel;
import com.asofdate.dispatch.model.TaskDependencyModel;
import com.asofdate.dispatch.service.BatchGroupService;
import com.asofdate.dispatch.service.TaskDependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by hzwy23 on 2017/5/27.
 */
@Service
public class TaskDependencyServiceImpl implements TaskDependencyService {
    @Autowired
    private TaskDependencyDao taskDependencyDao;
    @Autowired
    private BatchGroupService groupService;
    /*
    * key: 任务编码
    * value: 所有依赖的任务
    * */
    private Map<String, Set<String>> taskMap = new HashMap<>();

    @Override
    public List<TaskDependencyModel> findById(String domainId, String batchId) {
        return taskDependencyDao.findById(domainId, batchId);
    }

    public void afterPropertiesSet(String domainId, String batchId) {
        List<BatchGroupModel> groupList = groupService.findByBatchId(domainId, batchId);
        List<TaskDependencyModel> taskList = findById(domainId, batchId);

        /*
        * 初始化任务组中的任务依赖关系
        * */
        for (BatchGroupModel gt : groupList) {
            for (TaskDependencyModel m : taskList) {
                String id = join(gt.getUuid(), m.getId());
                if (this.taskMap.containsKey(id)) {
                    this.taskMap.get(id).add(join(gt.getUuid(), m.getUp_id()));
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(join(gt.getUuid(), m.getUp_id()));
                    this.taskMap.put(id, set);
                }
            }
        }
    }

    private String join(String... str1) {
        String result = "";
        for (String s : str1) {
            result += s + "__join__";
        }
        return result;
    }


    /*
    * 获取任务的依赖关系列表
    * @param String gid 表示任务组id
    * @param String id  表示任务id
    * */
    public Set<String> getTaskDependency(String gid, String id) {
        return this.taskMap.get(join(gid, id));
    }

}
