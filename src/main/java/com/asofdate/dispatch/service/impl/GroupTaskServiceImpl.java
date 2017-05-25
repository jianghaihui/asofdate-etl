package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.GroupTaskDao;
import com.asofdate.dispatch.model.BatchGroupModel;
import com.asofdate.dispatch.model.GroupTaskModel;
import com.asofdate.dispatch.service.BatchGroupService;
import com.asofdate.dispatch.service.GroupTaskService;
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
    @Autowired
    private GroupTaskDao groupTaskDao;

    @Autowired
    private BatchGroupService batchGroupService;

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
}
