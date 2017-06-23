package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.BatchGroupDao;
import com.asofdate.dispatch.model.BatchGroupModel;
import com.asofdate.dispatch.model.GroupDependencyModel;
import com.asofdate.dispatch.service.BatchGroupService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/25.
 */
@Service
public class BatchGroupServiceImpl implements BatchGroupService {
    @Autowired
    private BatchGroupDao batchGroupDao;

    @Override
    public List<BatchGroupModel> findByBatchId(String domainId, String batchId) {
        List<BatchGroupModel> list = batchGroupDao.findAll(domainId);
        for (int i = 0; i < list.size(); i++) {
            if (!batchId.equals(list.get(i).getBatchId())) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    @Override
    public List<BatchGroupModel> getGroup(String batchId) {
        return batchGroupDao.getGroup(batchId);
    }

    @Override
    public int addGroup(JSONArray jsonArray) {
        return batchGroupDao.addGroup(jsonArray);
    }

    @Override
    public int deleteGroup(JSONArray jsonArray) {
        return batchGroupDao.deleteGroup(jsonArray);
    }

    @Override
    public List<BatchGroupModel> getDependency(String batchid, String id) {
        return batchGroupDao.getDependency(batchid,id);
    }
}
