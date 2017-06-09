package com.asofdate.dispatch.support;

import com.asofdate.dispatch.model.BatchGroupModel;
import com.asofdate.dispatch.model.BatchStatus;
import com.asofdate.dispatch.service.BatchDefineService;
import com.asofdate.dispatch.service.GroupStatusService;
import com.asofdate.dispatch.service.TaskStatusService;
import com.asofdate.dispatch.support.utils.QuartzConfiguration;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by hzwy23 on 2017/5/25.
 */
@Component
@Scope("prototype")
public class JobScheduler extends Thread {

    private final Logger logger = LoggerFactory.getLogger(JobScheduler.class);
    // 批次运行对象
    private SchedulerFactoryBean scheduler;
    private QuartzConfiguration quartzConfiguration;

    private String domainId;
    private String batchId;
    private TaskStatusService taskStatus;
    private GroupStatusService groupStatus;
    private BatchDefineService batchDefineService;

    public void createJobSchedulerService(QuartzConfiguration quartzConfiguration,
                                          GroupStatusService groupStatus,
                                          TaskStatusService taskStatus,
                                          BatchDefineService batchDefineService) {
        this.batchId = quartzConfiguration.getBatchId();
        this.domainId = quartzConfiguration.getDomainId();
        this.quartzConfiguration = quartzConfiguration;
        this.scheduler = quartzConfiguration.getSchedulerFactoryBean();
        this.groupStatus = groupStatus;
        this.taskStatus = taskStatus;
        this.batchDefineService = batchDefineService;
    }

    @Override
    public void run() {
        if (domainId == null) {
            logger.error("domain id is empty. scheduler abort.");
            return;
        }

        if (batchId == null) {
            logger.error("batch id is null. scheduler abort.");
            return;
        }

        try {
            while (scheduler.isRunning()) {
                Map<String, BatchGroupModel> map = groupStatus.getRunnableGroups();
                for (BatchGroupModel m : map.values()) {
                    groupStatus.setGroupRunning(m.getUuid());
                    new RunGroupThread(scheduler.getScheduler(),
                            taskStatus,
                            groupStatus,
                            m.getUuid(),
                            m.getGroupId()).start();
                }

                if (taskStatus.isBatchCompleted()) {
                    logger.info("batch completed.");
                    logger.info("stop scheduler.");
                    scheduler.stop();
                    batchDefineService.setStatus(batchId, BatchStatus.BATCH_STATUS_COMPLETED);
                    return;
                }

                /*
                * 检查批次中是否有错误任务
                * 一旦出现任务执行错误,则停止批次
                * 并撤销整个批次
                * */
                if (taskStatus.isError()) {
                    logger.info("task error, 销毁批次");
                    scheduler.stop();
                    scheduler.destroy();
                    batchDefineService.setStatus(batchId, BatchStatus.BATCH_STATUS_ERROR);
                }
                logger.info("batch running. scanning runable group...");
                Thread.sleep(100);
                if (BatchStatus.BATCH_STATUS_RUNNING != batchDefineService.getStatus(batchId)) {
                    logger.info("batch status is not running");
                    scheduler.stop();
                    scheduler.destroy();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
