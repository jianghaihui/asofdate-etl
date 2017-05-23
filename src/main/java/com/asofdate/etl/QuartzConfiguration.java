package com.asofdate.etl;

import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class QuartzConfiguration extends DefaultBatchConfigurer {

    @Autowired
    public JobRegistry jobRegistry;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobLocator jobLocator;

    @Bean
    public JobRegistryBeanPostProcessor jobRepositoryFactoryBean(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(QuartzJobLauncher.class);
        jobDetailFactoryBean.setName("defaultJobAsOfDate");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jobLauncher", jobLauncher);
        map.put("jobLocator", jobLocator);
        map.put("jobRegistry", jobRegistry);
        jobDetailFactoryBean.setJobDataAsMap(map);
        jobDetailFactoryBean.afterPropertiesSet();
        return jobDetailFactoryBean;
    }

    public CronTriggerFactoryBean cronTriggerFactoryBean() throws ParseException {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean().getObject());
        cronTriggerFactoryBean.setCronExpression("*/10 * * * * ? *");
        cronTriggerFactoryBean.afterPropertiesSet();
        return cronTriggerFactoryBean;
    }

    public SimpleTriggerFactoryBean simpleTriggerFactoryBean() {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(jobDetailFactoryBean().getObject());
        simpleTriggerFactoryBean.setRepeatCount(0);
        simpleTriggerFactoryBean.setRepeatInterval(12);
        simpleTriggerFactoryBean.setGroup("etl");
        simpleTriggerFactoryBean.setName("simpleTrigger");
        simpleTriggerFactoryBean.afterPropertiesSet();
        return simpleTriggerFactoryBean;
    }

    public SchedulerFactoryBean schedulerFactoryBean() throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setAutoStartup(false);
        schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean().getObject());
        schedulerFactoryBean.afterPropertiesSet();
        return schedulerFactoryBean;
    }
}
