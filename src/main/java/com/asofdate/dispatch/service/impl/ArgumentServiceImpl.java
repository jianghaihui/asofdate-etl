package com.asofdate.dispatch.service.impl;

import com.asofdate.dispatch.model.BatchArgumentModel;
import com.asofdate.dispatch.model.TaskArgumentModel;
import com.asofdate.dispatch.service.ArgumentService;
import com.asofdate.dispatch.service.BatchArgumentService;
import com.asofdate.dispatch.service.TaskArgumentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/29.
 */
public class ArgumentServiceImpl implements ArgumentService {
    @Autowired
    private TaskArgumentService taskArgumentService;
    @Autowired
    private BatchArgumentService batchArgumentService;

    private String domainId;
    private String batchId;

    // 批次参数信息
    private List<BatchArgumentModel> batchList;
    // 任务参数信息
    private List<TaskArgumentModel> taskList;

    public void afterPropertySet(String domainId, String batchId) {
        this.domainId = domainId;
        this.batchId = batchId;

        this.batchList = batchArgumentService.findByBatchId(domainId, batchId);
        this.taskList = taskArgumentService.findByBatchId(domainId, batchId);
    }


}
