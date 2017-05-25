package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.TaskArgumentDao;
import com.asofdate.dispatch.model.GroupTaskModel;
import com.asofdate.dispatch.model.TaskArgumentModel;
import com.asofdate.dispatch.service.GroupTaskService;
import com.asofdate.dispatch.service.TaskArgumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzwy23 on 2017/5/25.
 */
@Service
public class TaskArgumentServiceImpl implements TaskArgumentService {
    @Autowired
    private TaskArgumentDao taskArgumentDao;

    @Autowired
    private GroupTaskService groupTaskService;

    @Override
    public List<TaskArgumentModel> findByBatchId(String domainId, String batchId) {

        List<GroupTaskModel> list = groupTaskService.findByBatchId(domainId, batchId);

        List<TaskArgumentModel> taskArgumentModelList = taskArgumentDao.findAll(domainId);

        Map<String, GroupTaskModel> map = new HashMap<String, GroupTaskModel>();
        for (GroupTaskModel m : list) {
            if (!map.containsKey(m.getTask_id())) {
                map.put(m.getTask_id(), m);
            }
        }

        for (int i = 0; i < taskArgumentModelList.size(); i++) {
            if (!map.containsKey(taskArgumentModelList.get(i).getTask_id())) {
                taskArgumentModelList.remove(i);
                i--;
            }
        }
        return taskArgumentModelList;
    }
}
