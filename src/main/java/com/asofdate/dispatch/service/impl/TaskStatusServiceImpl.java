package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.BatchJobStatusDao;
import com.asofdate.dispatch.model.BatchGroupModel;
import com.asofdate.dispatch.model.GroupTaskModel;
import com.asofdate.dispatch.model.JobStatus;
import com.asofdate.dispatch.service.BatchGroupService;
import com.asofdate.dispatch.service.GroupTaskService;
import com.asofdate.dispatch.service.TaskDependencyService;
import com.asofdate.dispatch.service.TaskStatusService;
import com.asofdate.utils.JoinCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hzwy23 on 2017/5/28.
 */
@Service
@Scope("prototype")
public class TaskStatusServiceImpl implements TaskStatusService {
    @Autowired
    private BatchGroupService groupService;
    @Autowired
    private GroupTaskService taskService;
    @Autowired
    private TaskDependencyService taskDependencyService;
    @Autowired
    private BatchJobStatusDao batchJobStatusDao;

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
                if (gl.getGroupId().equals(tl.getGroupId())) {
                    // 0 表示初始化
                    this.taskMap.put(JoinCode.join(gl.getUuid(), tl.getUuid()), JobStatus.Job_STATUS_INIT);
                }
            }
        }
        batchJobStatusDao.init(batchId, this.taskMap);
        this.taskDependencyService.afterPropertiesSet(domainId, batchId);
    }

    /*
    * 判断任务组中所有的任务是否执行完成
    * 如果任务组中所有的任务都执行完成
    * 则表示这个任务组执行完成
    * gid 表示 任务组中任务的id
    * groupId 表示任务组的编码
    * */
    @Override
    public boolean isGroupCompleted(String gid, String groupId) {
        for (GroupTaskModel m : taskList) {
            if (groupId.equals(m.getGroupId())) {
                int statusCd = getTaskStatus(gid, m.getUuid());
                if (statusCd == JobStatus.Job_STATUS_COMPLETED) {
                    continue;
                }
                return false;
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
        if (taskMap.containsKey(JoinCode.join(gid, id))) {
            return taskMap.get(JoinCode.join(gid, id));
        }
        return JobStatus.Job_STATUS_ERROR;
    }


    /*
    * @param String uid 表示任务组id与任务id的组合,简称uid
    * */
    private int getTaskStatus2(String uid) {
        if (taskMap.containsKey(uid)) {
            return taskMap.get(uid);
        }
        return JobStatus.Job_STATUS_ERROR;
    }


    /*
    * 判断任务组中的任务是否可以执行
    * @param String gid 表示任务组的id;
    * @param String id  表示任务的id;
    * */
    @Override
    public boolean isTaskRunnable(String gid, String id) {
        Set<String> taskDependencyModelSet = taskDependencyService.getTaskDependency(gid, id);
        if (taskDependencyModelSet == null) {
            return true;
        }
        for (String m : taskDependencyModelSet) {
            if (JobStatus.Job_STATUS_COMPLETED == getTaskStatus2(m)) {
                continue;
            }
            return false;
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
        this.taskMap.put(uid, JobStatus.Job_STATUS_COMPLETED);
        batchJobStatusDao.setJobStatus(batchId, uid, JobStatus.Job_STATUS_COMPLETED);
    }

    @Override
    public void setTaskRunning(String uid) {
        this.taskMap.put(uid, JobStatus.Job_STATUS_RUNNING);
        batchJobStatusDao.setJobStatus(batchId, uid, JobStatus.Job_STATUS_RUNNING);
    }

    @Override
    public void setTaskError(String uid) {
        this.taskMap.put(uid, JobStatus.Job_STATUS_ERROR);
        batchJobStatusDao.setJobStatus(batchId, uid, JobStatus.Job_STATUS_ERROR);
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
            if (!groupId.equals(m.getGroupId())) {
                continue;
            }
            map.remove(m.getUuid());
            if (getTaskStatus(gid, m.getUuid()) != JobStatus.Job_STATUS_INIT) {
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
            if (entry.getValue() != JobStatus.Job_STATUS_COMPLETED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isError() {
        return taskMap.containsValue(JobStatus.Job_STATUS_ERROR);
    }

}
