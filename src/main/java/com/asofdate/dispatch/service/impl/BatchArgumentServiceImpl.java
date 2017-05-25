package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.dao.BatchArgumentDao;
import com.asofdate.dispatch.model.BatchArgumentModel;
import com.asofdate.dispatch.service.BatchArgumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/25.
 */
@Service
public class BatchArgumentServiceImpl implements BatchArgumentService {
    @Autowired
    private BatchArgumentDao batchArgumentDao;

    @Override
    public List<BatchArgumentModel> findByBatchId(String domainId, String batchId) {
        List<BatchArgumentModel> list = batchArgumentDao.findAll(domainId);
        for (int i = 0; i < list.size(); i++) {
            if (!batchId.equals(list.get(i).getBatch_id())) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }
}
