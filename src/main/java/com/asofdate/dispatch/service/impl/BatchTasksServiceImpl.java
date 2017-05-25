package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.BatchGroupDao;
import com.asofdate.dispatch.dao.GroupTaskDao;
import com.asofdate.dispatch.model.BatchGroupModel;
import com.asofdate.dispatch.model.GroupTaskModel;
import com.asofdate.dispatch.service.BatchTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzwy23 on 2017/5/24.
 */
@Service
public class BatchTasksServiceImpl implements BatchTasksService {
    @Autowired
    private BatchGroupDao batchGroupDao;
    @Autowired
    private GroupTaskDao groupTaskDao;


    /*
    * @param domainId String 域编码
    * @param batchId String  批次编码
    * @return List<String> 返回所有任务的列表信息
    * */
    @Override
    public List findAllTasks(String domainId, String batchId) {
        List<BatchGroupModel> bglist = batchGroupDao.findAll(domainId);
        Map<String, BatchGroupModel> bgmap = new HashMap<String, BatchGroupModel>();
        for (BatchGroupModel m : bglist) {
            if (batchId.equals(m.getBatch_id())) {
                bgmap.put(m.getGroup_id(), m);
            }
        }

        List<String> result = new ArrayList<String>();
        List<GroupTaskModel> gtlist = groupTaskDao.findAll(domainId);
        for (GroupTaskModel m : gtlist) {
            if (bgmap.containsKey(m.getGroup_id())) {
                if (!result.contains(m.getTask_id())) {
                    result.add(m.getTask_id());
                }
            }
        }
        return result;
    }
}
