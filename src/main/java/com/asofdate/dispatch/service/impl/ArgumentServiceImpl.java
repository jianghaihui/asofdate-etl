package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.ArgumentDefineDao;
import com.asofdate.dispatch.dao.BatchArgumentDao;
import com.asofdate.dispatch.dao.GroupArgumentDao;
import com.asofdate.dispatch.dao.TaskArgumentDao;
import com.asofdate.dispatch.model.*;
import com.asofdate.dispatch.service.ArgumentService;
import com.asofdate.dispatch.service.GroupTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzwy23 on 2017/5/29.
 */
@Service
@Scope("prototype")
public class ArgumentServiceImpl implements ArgumentService {
    private final String BATCH_ARGUMENT = "4";
    private final String GROUP_ARGUMENT = "3";
    private final String TASK_ARGUMENT = "2";
    private final String FIXED_ARGUMENT = "1";
    @Autowired
    private TaskArgumentDao taskArgumentDao;
    @Autowired
    private BatchArgumentDao batchArgumentDao;
    @Autowired
    private ArgumentDefineDao argumentDefineDao;
    @Autowired
    private GroupArgumentDao groupArgumentDao;
    @Autowired
    private GroupTaskService groupTaskService;

    private String domainId;
    private String batchId;

    private Map<String, GroupTaskModel> groupTaskMap;

    // 参数定义
    private List<ArgumentDefineModel> argDefineList;
    private Map<String, ArgumentDefineModel> argDefineMap;
    // 批次参数信息
    private List<BatchArgumentModel> batchList;

    // 任务参数信息
    private List<TaskArgumentModel> taskList;
    private Map<String, List<TaskArgumentModel>> taskMap;

    // 任务组参数
    private Map<String, List<GroupArgumentModel>> groupArgumentMap;

    public void afterPropertySet(String domainId, String batchId) {
        this.groupTaskMap = new HashMap<>();
        this.domainId = domainId;
        this.batchId = batchId;
        this.argDefineList = this.argumentDefineDao.findAll(domainId);
        this.batchList = this.batchArgumentDao.findAll(domainId);
        this.groupArgumentMap = new HashMap<>();
        for (GroupArgumentModel m : this.groupArgumentDao.findAll(domainId)) {
            if (this.groupArgumentMap.containsKey(m.getId())) {
                this.groupArgumentMap.get(m.getId()).add(m);
            } else {
                List<GroupArgumentModel> l = new ArrayList<>();
                l.add(m);
                this.groupArgumentMap.put(m.getId(), l);
            }
        }

        List<GroupTaskModel> list = this.groupTaskService.findByBatchId(domainId, batchId);
        for (GroupTaskModel m : list) {
            this.groupTaskMap.put(m.getUuid(), m);
        }

        for (int i = 0; i < this.batchList.size(); i++) {
            if (!batchId.equals(this.batchList.get(i).getBatch_id())) {
                this.batchList.remove(i);
                i--;
            }
        }

        this.taskList = this.taskArgumentDao.findAll(domainId);

        initArgDefineMap();
        initTaskArgMap();


        /*
        * for test
        * */
//        System.out.println("***************************");
//        for(GroupTaskModel m: groupTaskMap.values()){
//            System.out.println(m.getGroup_id()+","+m.getTask_id());
//        }
//        System.out.println("****************************");
//        for (String m: argDefineMap.keySet()){
//            System.out.println("key is :" + m);
//        }
//        for(ArgumentDefineModel m:argDefineMap.values()){
//            System.out.print(m.getArg_id()+",");
//            System.out.println(m.getArg_value());
//        }
//        System.out.println("****************************");
//        for(List<TaskArgumentModel> m: taskMap.values()){
//            for(TaskArgumentModel l:m){
//                System.out.print(l.getArg_id()+",");
//                System.out.println(l.getArg_value());
//            }
//        }
    }

    @Override
    public List<ArgumentDefineModel> findAll(String domainID) {
        return argumentDefineDao.findAll(domainID);
    }

    @Override
    public int add(ArgumentDefineModel m) {
        return argumentDefineDao.add(m);
    }

    @Override
    public String delete(List<ArgumentDefineModel> m) {
        return argumentDefineDao.delete(m);
    }

    @Override
    public int update(ArgumentDefineModel m) {
        return argumentDefineDao.update(m);
    }

    private void initArgDefineMap() {
        /*
        * 修改参数定义中的参数类型为批次参数的值
        * */
        Map<String, BatchArgumentModel> map = new HashMap<>();
        for (BatchArgumentModel m : batchList) {
            if (this.batchId.equals(m.getBatch_id())) {
                map.put(m.getArg_id(), m);
            }
        }

        argDefineMap = new HashMap<>();
        for (ArgumentDefineModel m : argDefineList) {
            if (BATCH_ARGUMENT.equals(m.getArg_type())) {
                m.setArg_value(map.get(m.getArg_id()).getArg_value());
            }
            argDefineMap.put(m.getArg_id(), m);
        }
    }

    private void initTaskArgMap() {
        taskMap = new HashMap<>();
        for (TaskArgumentModel m : taskList) {
            if (taskMap.containsKey(m.getTask_id())) {
                taskMap.get(m.getTask_id()).add(m);
            } else {
                List<TaskArgumentModel> l = new ArrayList<>();
                l.add(m);
                taskMap.put(m.getTask_id(), l);
            }
        }
    }

    /*
    * 根据任务组中任务唯一编码获取其需要的参数
    * 参数分为三个级别
    * 1: 批次参数
    * 2: 任务组中配置任务的参数
    * 3: 固定参数
    * */
    @Override
    public List<TaskArgumentModel> getArgument(String id) {
        String taskId = this.groupTaskMap.get(id).getTask_id();
        if (taskId == null) {
            return null;
        }
        List<TaskArgumentModel> list = taskMap.get(taskId);
        if (list == null) {
            return null;
        }
        for (TaskArgumentModel m : list) {
            switch (argDefineMap.get(m.getArg_id()).getArg_type()) {
                case GROUP_ARGUMENT:
                    for (GroupArgumentModel g : this.groupArgumentMap.get(id)) {
                        if (g.getArg_id().equals(m.getArg_id())) {
                            m.setArg_value(g.getArg_value());
                            break;
                        }
                    }
                    break;
                case FIXED_ARGUMENT:
                case BATCH_ARGUMENT:
                    m.setArg_value(this.argDefineMap.get(m.getArg_id()).getArg_value());
                    break;
            }
        }
        return list;
    }

}
