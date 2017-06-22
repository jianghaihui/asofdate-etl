package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.model.BatchStatus;
import com.asofdate.dispatch.service.ArgumentService;
import com.asofdate.dispatch.service.BatchDefineService;
import com.asofdate.dispatch.service.GroupStatusService;
import com.asofdate.dispatch.service.TaskStatusService;
import com.asofdate.dispatch.support.JobScheduler;
import com.asofdate.dispatch.support.utils.QuartzConfiguration;
import com.asofdate.utils.Hret;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hzwy23 on 2017/5/23.
 */
@Controller
@Scope("prototype")
public class DispatchController {
    private final Logger logger = LoggerFactory.getLogger(DispatchController.class);

    @Autowired
    public QuartzConfiguration quartzConfiguration;

    @Autowired
    private JobScheduler jobScheduler;
    @Autowired
    private TaskStatusService taskStatus;
    @Autowired
    private GroupStatusService groupStatus;
    @Autowired
    private ArgumentService argumentService;
    @Autowired
    private BatchDefineService batchDefineService;

    @RequestMapping(value = "/v1/dispatch/start")
    @ResponseBody
    public String start(HttpServletResponse response, HttpServletRequest request) throws Exception {

        String domainId = request.getParameter("domain_id");
        String batchId = request.getParameter("batch_id");

        if (domainId == null || batchId == null) {
            response.setStatus(421);
            logger.info("batch id or domain id is null ,batch id is: {}, domain id is: {}", batchId, domainId);
            return Hret.error(421, "domain_id is empty or batch_id is empty", JSONObject.NULL);
        }

        if (BatchStatus.BATCH_STATUS_RUNNING == batchDefineService.getStatus(batchId)) {
            response.setStatus(421);
            logger.info("batch is running,batch_id is: {}", batchId);
            return Hret.error(421, "批次正在运行中", JSONObject.NULL);
        }

        int size = batchDefineService.runBatchInit(batchId);
        if ( 1 != size ){
            logger.info("批次日期大于终止日期,无法运行");
            response.setStatus(426);
            return Hret.error(426,"批次日期大于终止日期,批次无法运行",null);
        }

        groupStatus.afterPropertiesSet(domainId, batchId);
        taskStatus.afterPropertiesSet(domainId, batchId);
        argumentService.afterPropertySet(domainId, batchId);

        // 由于初始化时关闭了所有的触发器
        // 所以,调度开启后,并不会有任务执行
        quartzConfiguration.createSchedulerFactoryBean(domainId, batchId, taskStatus, argumentService);

        // 进度调度依赖关系管理
        // 根据依赖关系,开启任务触发器
        jobScheduler.createJobSchedulerService(quartzConfiguration, groupStatus, taskStatus, batchDefineService);
        jobScheduler.start();

        logger.info("batch started, batch_id is:{},domain_id is:{}", batchId, domainId);
        return Hret.success(200, "start batch successfully. batch id is :" + batchId, JSONObject.NULL);
    }
}
