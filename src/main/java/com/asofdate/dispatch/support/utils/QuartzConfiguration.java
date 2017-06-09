package com.asofdate.dispatch.support.utils;

import com.asofdate.dispatch.model.GroupTaskModel;
import com.asofdate.dispatch.model.TaskDefineModel;
import com.asofdate.dispatch.service.ArgumentService;
import com.asofdate.dispatch.service.GroupTaskService;
import com.asofdate.dispatch.service.TaskDefineService;
import com.asofdate.dispatch.service.TaskStatusService;
import com.asofdate.utils.JoinCode;
import org.quartz.SimpleTrigger;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@Scope("prototype")
public class QuartzConfiguration extends DefaultBatchConfigurer {
    @Autowired
    private BatchConfiguration batchConfiguration;
    @Autowired
    private TaskDefineService taskDefineService;
    @Autowired
    private GroupTaskService groupTaskService;

    private String domainId;
    private String batchId;
    private SchedulerFactoryBean schedulerFactoryBean;
    private TaskStatusService taskStatusService;
    private ArgumentService argumentService;
    private Map<String, TaskDefineModel> taskDefineMap;
    private Map<String, GroupTaskModel> groupTaskMap;

    public SchedulerFactoryBean getSchedulerFactoryBean() {
        return schedulerFactoryBean;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    private JobDetailFactoryBean jobDetailFactoryBean(String jobName) {

        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(QuartzJobLauncher.class);
        jobDetailFactoryBean.setDurability(true);

        jobDetailFactoryBean.setName(jobName);
        JobRepository jobRepository = batchConfiguration.createJobRepository();
        JobLauncher jobLauncher = batchConfiguration.createJobLauncher(jobRepository);
        String taskId = this.groupTaskMap.get(JoinCode.getTaskCode(jobName)).getTaskId();

        TaskDefineModel tm = this.taskDefineMap.get(taskId);
        String typeId = tm.getTaskType();
        String scriptFile = tm.getScriptFile();

        JobRegistry jobRegistry = batchConfiguration.createJobRegistry(jobName, typeId, scriptFile);
        JobExplorer jobExplorer = batchConfiguration.createJobExplorer();
        JobOperator jobOperator = batchConfiguration.createJobOperator(jobLauncher, jobExplorer, jobRegistry, jobRepository);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jobName", jobName);
        map.put("jobLauncher", jobLauncher);
        map.put("jobRegistry", jobRegistry);
        map.put("jobExplorer", jobExplorer);
        map.put("jobOperator", jobOperator);
        map.put("taskStatusService", taskStatusService);
        map.put("argumentService", argumentService);
        jobDetailFactoryBean.setJobDataAsMap(map);

        jobDetailFactoryBean.afterPropertiesSet();
        return jobDetailFactoryBean;
    }


    private SimpleTrigger createSimpleTrigger(String jobName) {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(jobDetailFactoryBean(jobName).getObject());
        simpleTriggerFactoryBean.setRepeatCount(0);
        simpleTriggerFactoryBean.setRepeatInterval(12);
        simpleTriggerFactoryBean.setGroup(jobName);
        simpleTriggerFactoryBean.setName(jobName);
        simpleTriggerFactoryBean.afterPropertiesSet();
        return simpleTriggerFactoryBean.getObject();
    }


    public SchedulerFactoryBean createSchedulerFactoryBean(String domainId, String batchId, TaskStatusService taskStatusService, ArgumentService argumentService) throws Exception {
        setDomainId(domainId);
        setBatchId(batchId);
        this.taskStatusService = taskStatusService;
        this.argumentService = argumentService;
        this.taskDefineMap = new HashMap<>();
        List<TaskDefineModel> list = taskDefineService.findAll(domainId, batchId);
        for (TaskDefineModel m : list) {
            this.taskDefineMap.put(m.getTaskId(), m);
        }

        this.groupTaskMap = new HashMap<>();
        List<GroupTaskModel> gtlist = groupTaskService.findByBatchId(domainId, batchId);
        for (GroupTaskModel m : gtlist) {
            this.groupTaskMap.put(m.getUuid(), m);
        }

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // 配置DataSource后,将会出现异常
        // JobLauncher实例化对象无法序列化
        // schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setSchedulerName(batchId);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactoryBean.setAutoStartup(false);

        // 获取指定批次的所有任务Job

        ArrayList<SimpleTrigger> arrayList = new ArrayList<SimpleTrigger>();
        for (String jobId : taskStatusService.getAll().keySet()) {
            SimpleTrigger simpleTrigger = createSimpleTrigger(jobId);
            schedulerFactoryBean.setTriggers(simpleTrigger);
            arrayList.add(simpleTrigger);
        }

        SimpleTrigger[] simpleTriggers = new SimpleTrigger[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            simpleTriggers[i] = arrayList.get(i);
        }
        schedulerFactoryBean.setTriggers(simpleTriggers);
        schedulerFactoryBean.afterPropertiesSet();
        schedulerFactoryBean.getScheduler().pauseAll();
        schedulerFactoryBean.start();
        this.schedulerFactoryBean = schedulerFactoryBean;
        return schedulerFactoryBean;
    }

//    private CronTrigger createCronTrigger(String jobName) throws ParseException {
//        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
//        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean(jobName).getObject());
//        cronTriggerFactoryBean.setCronExpression("*/10 * * * * ? *");
//        cronTriggerFactoryBean.afterPropertiesSet();
//        return cronTriggerFactoryBean.getObject();
//    }
}
