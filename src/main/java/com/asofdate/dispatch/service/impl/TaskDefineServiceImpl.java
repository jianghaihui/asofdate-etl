package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.TaskArgumentDao;
import com.asofdate.dispatch.dao.TaskDefineDao;
import com.asofdate.dispatch.model.GroupTaskModel;
import com.asofdate.dispatch.model.TaskDefineModel;
import com.asofdate.dispatch.service.GroupTaskService;
import com.asofdate.dispatch.service.TaskDefineService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzwy23 on 2017/5/24.
 */
@Service
public class TaskDefineServiceImpl implements TaskDefineService {
    @Autowired
    private TaskDefineDao dispatchTaskDefineDao;

    @Autowired
    private GroupTaskService groupTaskService;

    @Autowired
    private TaskArgumentDao taskArgumentDao;

    @Override
    public List<TaskDefineModel> findAll(String domainId, String batchId) {
        List<TaskDefineModel> list = dispatchTaskDefineDao.findAll(domainId);
        List<GroupTaskModel> groupTaskModelList = groupTaskService.findByBatchId(domainId, batchId);
        Map<String, GroupTaskModel> map = new HashMap<String, GroupTaskModel>();

        for (GroupTaskModel m : groupTaskModelList) {
            if (!map.containsKey(m.getTask_id())) {
                map.put(m.getTask_id(), m);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (!map.containsKey(list.get(i).getTaskId())) {
                list.remove(i);
                i--;
            }
        }

        return list;
    }

    @Override
    public List<TaskDefineModel> getAll(String domainId) {
        return dispatchTaskDefineDao.findAll(domainId);
    }

    @Override
    public int add(TaskDefineModel m) {
        return dispatchTaskDefineDao.add(m);
    }

    @Override
    public String delete(List<TaskDefineModel> m) {
        return dispatchTaskDefineDao.delete(m);
    }

    @Override
    public int update(TaskDefineModel m) {
        return dispatchTaskDefineDao.update(m);
    }

    @Override
    public JSONArray getTaskArg(String taskId) {
        return taskArgumentDao.getTaskArg(taskId);
    }
}
