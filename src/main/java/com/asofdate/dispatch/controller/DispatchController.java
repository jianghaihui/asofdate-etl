package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.support.JobScheduler;
import com.asofdate.dispatch.support.QuartzConfiguration;
import com.asofdate.utils.Hret;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hzwy23 on 2017/5/23.
 */
@Controller
@Scope("prototype")
public class DispatchController {

    @Autowired
    public QuartzConfiguration quartzConfiguration;

    @Autowired
    private JobScheduler jobScheduler;

    @RequestMapping(value = "/v1/dispatch/start")
    @ResponseBody
    public String start(HttpServletRequest request) throws Exception {

        String domainId = request.getParameter("domain_id");
        String batchId = request.getParameter("batch_id");

        if (domainId == null || batchId == null) {
            return Hret.error(421, "domain_id is empty or batch_id is empty", JSONObject.NULL);
        }

        // 由于初始化时关闭了所有的触发器
        // 所以,调度开启后,并不会有任务执行
        SchedulerFactoryBean schedulerFactoryBean = quartzConfiguration.createSchedulerFactoryBean(domainId,batchId);

        // 进度调度依赖关系管理
        // 根据依赖关系,开启任务触发器
        jobScheduler.initJobSchedulerService(quartzConfiguration);
        jobScheduler.start();

        return Hret.success(200, "start batch successfully. batch id is :" + batchId, JSONObject.NULL);
    }
}
