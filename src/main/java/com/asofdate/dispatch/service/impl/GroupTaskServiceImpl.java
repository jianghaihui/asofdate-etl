package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.GroupArgumentDao;
import com.asofdate.dispatch.dao.GroupTaskDao;
import com.asofdate.dispatch.dao.TaskArgumentDao;
import com.asofdate.dispatch.model.BatchGroupModel;
import com.asofdate.dispatch.model.GroupTaskModel;
import com.asofdate.dispatch.service.BatchGroupService;
import com.asofdate.dispatch.service.GroupTaskService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzwy23 on 2017/5/25.
 */
@Service
public class GroupTaskServiceImpl implements GroupTaskService {
    private final Logger logger = LoggerFactory.getLogger(GroupTaskServiceImpl.class);
    @Autowired
    private GroupTaskDao groupTaskDao;

    @Autowired
    private BatchGroupService batchGroupService;

    @Autowired
    private TaskArgumentDao taskArgumentDao;

    @Autowired
    private GroupArgumentDao groupArgumentDao;

    @Override
    public List<GroupTaskModel> findByBatchId(String domainId, String batchId) {
        List<GroupTaskModel> list = groupTaskDao.findAll(domainId);

        List<BatchGroupModel> batchGroupModelList = batchGroupService.findByBatchId(domainId, batchId);
        Map<String, BatchGroupModel> map = new HashMap<String, BatchGroupModel>();
        for (BatchGroupModel m : batchGroupModelList) {
            if (!map.containsKey(m.getGroup_id())) {
                map.put(m.getGroup_id(), m);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (!map.containsKey(list.get(i).getGroup_id())) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    @Override
    public JSONArray getTask(String groupId) {
        return groupTaskDao.getTask(groupId);
    }

    @Override
    public JSONArray getTaskArg(String id) {
        JSONArray ret = new JSONArray();
        String taskId = groupTaskDao.getTaskId(id);
        logger.info("task id is :" + taskId);
        JSONArray jsonArray = groupArgumentDao.getGroupArg(id);
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            map.put(jsonObject.getString("arg_id"), jsonObject.getString("arg_value"));
        }
        logger.info("group arg is :" + jsonArray.toString());
        JSONArray taskArgList = taskArgumentDao.getTaskArg(taskId);
        logger.info("task arg  is :" + taskArgList.toString());
        for (int i = 0; i < taskArgList.length(); i++) {
            JSONObject one = (JSONObject) taskArgList.get(i);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid", one.getString("uuid"));
            jsonObject.put("task_id", one.getString("task_id"));
            jsonObject.put("arg_id", one.getString("arg_id"));
            switch (one.getString("arg_type")) {
                case "1":
                case "2":
                    jsonObject.put("arg_value", one.getString("arg_value"));
                    break;
                case "3":
                    if (map.containsKey(one.getString("arg_id"))) {
                        String argValue = map.get(one.getString("arg_id"));
                        jsonObject.put("arg_value", argValue);
                        break;
                    }
                default:
                    jsonObject.put("arg_value", "-");
            }

            jsonObject.put("sort_id", one.getString("sort_id"));
            jsonObject.put("domain_id", one.getString("domain_id"));
            jsonObject.put("arg_type", one.getString("arg_type"));
            jsonObject.put("arg_type_desc", one.getString("arg_type_desc"));
            jsonObject.put("arg_desc", one.getString("arg_desc"));
            jsonObject.put("code_number", one.getString("code_number"));
            ret.put(jsonObject);
        }
        return ret;
    }

    @Override
    public int deleteTask(String id) {
        return groupTaskDao.deleteTask(id);
    }

    @Override
    public int addTask(String id, String groupId, String taskId, String domainId) {
        return groupTaskDao.addTask(id, groupId, taskId, domainId);
    }

    @Override
    public int addGroupArg(JSONArray jsonArray) {
        return groupTaskDao.addArg(jsonArray);
    }
}
