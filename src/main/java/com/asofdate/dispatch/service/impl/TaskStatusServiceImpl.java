package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.model.BatchGroupModel;
import com.asofdate.dispatch.model.GroupTaskModel;
import com.asofdate.dispatch.service.BatchGroupService;
import com.asofdate.dispatch.service.GroupTaskService;
import com.asofdate.dispatch.service.TaskDependencyService;
import com.asofdate.dispatch.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hzwy23 on 2017/5/28.
 */
@Service
public class TaskStatusServiceImpl implements TaskStatusService {
    @Autowired
    private BatchGroupService groupService;
    @Autowired
    private GroupTaskService taskService;
    @Autowired
    private TaskDependencyService taskDependencyService;

    private String domainId;
    private String batchId;

    private List<BatchGroupModel> groupList;
    // 任务组中的任务
    private List<GroupTaskModel> taskList;
    /*
    * 批次中任务状态管理
    * 外层Map是batch中group的嵌套
    * key 表示任务组中任务的id,每一个任务的id都不一样
    * value: 表示任务的状态.
    *       0: 初始化
    *       1: 运行中
    *       2: 已完成
    *       3: 错误
    * */
    private Map<String, Integer> taskMap = new HashMap<String, Integer>();

    @Override
    public Map<String, Integer> getAll() {
        return this.taskMap;
    }

    // 初始化两个变量
    @Override
    public void afterPropertiesSet(String domainId, String batchId) {
        this.domainId = domainId;
        this.batchId = batchId;
        this.groupList = groupService.findByBatchId(domainId, batchId);
        this.taskList = taskService.findByBatchId(domainId, batchId);

        /*
        * 初始化全部任务组
        * */
        for (BatchGroupModel gl : this.groupList) {
            for (GroupTaskModel tl : this.taskList) {
                if (gl.getGroup_id().equals(tl.getGroup_id())) {
                    // 0 表示初始化
                    this.taskMap.put(join(gl.getUuid(), tl.getUuid()), 0);
                }
            }
        }

        this.taskDependencyService.afterPropertiesSet(domainId, batchId);
    }

    /*
    * 判断任务组中所有的任务是否执行完成
    * 如果任务组中所有的任务都执行完成
    * 则表示这个任务组执行完成
    * */
    @Override
    public boolean isGroupCompleted(String gid, String groupId) {
        for (GroupTaskModel m : taskList) {
            if (groupId.equals(m.getGroup_id())) {
                switch (getTaskStatus(gid, m.getUuid())) {
                    case 0:
                        return false;
                    case 1:
                        return false;
                    case 2:
                        break;
                    case 3:
                        return false;
                    default:
                        return false;
                }
            }
        }
        return true;
    }

    /*
    * 获取任务状态
    * @param String gid 表示任务组id
    * @param String id  表示任务id;
    * */
    public int getTaskStatus(String gid, String id) {
        if (this.taskMap.containsKey(join(gid, id))) {
            return this.taskMap.get(join(gid, id));
        }
        return 3;
    }


    /*
    * @param String uid 表示任务组id与任务id的组合,简称uid
    * */
    private int getTaskStatus2(String uid) {
        if (this.taskMap.containsKey(uid)) {
            return this.taskMap.get(uid);
        }
        return 3;
    }


    /*
    * 判断任务组中的任务是否可以执行
    * @param String gid 表示任务组的id;
    * @param String id  表示任务的id;
    * */
    @Override
    public boolean isTaskRunnable(String gid, String id) {
        Set<String> taskDependencyModelSet = this.taskDependencyService.getTaskDependency(gid, id);
        if (taskDependencyModelSet == null) {
            return true;
        }
        for (String m : taskDependencyModelSet) {
            switch (getTaskStatus2(m)) {
                case 0:
                    return false;
                case 1:
                    return false;
                case 2:
                    break;
                case 3:
                    return false;
                default:
                    return false;
            }
        }
        return true;
    }


    /*
    * 设置任务状态为已完成
    * @param String gid 表示任务组id
    * @param String id  表示任务id
    * */
    @Override
    public void setTaskCompleted(String uid) {
        this.taskMap.put(uid, 2);
    }

    @Override
    public void setTaskRunning(String uid) {
        this.taskMap.put(uid, 1);
    }

    @Override
    public void setTaskError(String uid) {
        this.taskMap.put(uid,3);
    }


    /*
    * @return Map<Key,Value>
    *     key: 表示配置任务的id号,这个id号不是任务编码,而是配置任务依赖时,随机产生的系统唯一性编码
    *     value: 是配置的任务相信信息,包括任务编码,所属域
    * */
    @Override
    public Map getRunnableTasks(String gid, String groupId) {
        Map<String, GroupTaskModel> map = new HashMap<>();
        for (GroupTaskModel m : taskList) {
            if (!groupId.equals(m.getGroup_id())) {
                continue;
            }
            map.remove(m.getUuid());
            if (getTaskStatus(gid, m.getUuid()) != 0) {
                continue;
            }
            if (isTaskRunnable(gid, m.getUuid())) {
                map.put(m.getUuid(), m);
            }
        }
        return map;
    }

    @Override
    public boolean isBatchCompleted() {
        for (Map.Entry<String, Integer> entry : taskMap.entrySet()) {
            if (entry.getValue() != 2) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isError() {
        return this.taskMap.containsValue(3);
    }

    private String join(String... str1) {
        String result = "";
        for (String s : str1) {
            result += s + "__join__";
        }
        return result;
    }

}
