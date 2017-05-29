package com.asofdate.dispatch.controller;

import com.asofdate.dispatch.service.GroupStatusService;
import com.asofdate.dispatch.service.TaskStatusService;
import com.asofdate.dispatch.support.JobScheduler;
import com.asofdate.dispatch.support.utils.QuartzConfiguration;
import com.asofdate.utils.Hret;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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

    @Autowired
    private TaskStatusService taskStatus;
    @Autowired
    private GroupStatusService groupStatus;

    @RequestMapping(value = "/v1/dispatch/start")
    @ResponseBody
    public String start(HttpServletRequest request) throws Exception {

        String domainId = request.getParameter("domain_id");
        String batchId = request.getParameter("batch_id");

        if (domainId == null || batchId == null) {
            return Hret.error(421, "domain_id is empty or batch_id is empty", JSONObject.NULL);
        }

        /*
        * 服务流程:
        *     1. 初始化任务组
        *     2. 初始化任务
        *     3. 注册任务
        *     4. 初始化调度器
        *     5. 初始化任务控制服务
        *     6. 开启服务
        * */
        groupStatus.afterPropertiesSet(domainId, batchId);
        taskStatus.afterPropertiesSet(domainId, batchId);
        // 由于初始化时关闭了所有的触发器
        // 所以,调度开启后,并不会有任务执行
        quartzConfiguration.createSchedulerFactoryBean(domainId, batchId, taskStatus);

        // 进度调度依赖关系管理
        // 根据依赖关系,开启任务触发器
        jobScheduler.createJobSchedulerService(quartzConfiguration, groupStatus, taskStatus);
        jobScheduler.start();

        return Hret.success(200, "start batch successfully. batch id is :" + batchId, JSONObject.NULL);
    }
}
