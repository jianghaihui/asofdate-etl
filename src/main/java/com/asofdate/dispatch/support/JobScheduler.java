package com.asofdate.dispatch.support;

import com.asofdate.dispatch.model.BatchArgumentModel;
import com.asofdate.dispatch.model.BatchGroupModel;
import com.asofdate.dispatch.model.GroupTaskModel;
import com.asofdate.dispatch.model.TaskArgumentModel;
import com.asofdate.dispatch.service.BatchArgumentService;
import com.asofdate.dispatch.service.BatchGroupService;
import com.asofdate.dispatch.service.GroupTaskService;
import com.asofdate.dispatch.service.TaskArgumentService;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;

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

    /************************************************************************/
    @Autowired
    private BatchGroupService batchGroupService;
    @Autowired
    private GroupTaskService groupTaskService;
    @Autowired
    private TaskArgumentService taskArgumentService;
    @Autowired
    private BatchArgumentService batchArgumentService;
    /*************************************************************************/

    // 批次对应的任务组
    private List<BatchGroupModel> batchGroupModelList;
    // 任务组中的任务
    private List<GroupTaskModel> groupTaskModelList;
    // 任务参数信息
    private List<TaskArgumentModel> taskArgumentModelList;
    // 批次参数信息
    private List<BatchArgumentModel> batchArgumentModelList;

    /********************************* getter and setter ********************/
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

    /******************************** end setter and setter *****************/

    private void init() {
        /*
        * 初始化batchGroupModelList.
        * 获取这个批次所有的任务组信息
        * */
        this.batchGroupModelList = batchGroupService.findByBatchId(domainId, batchId);
        this.groupTaskModelList = groupTaskService.findByBatchId(domainId, batchId);
        this.taskArgumentModelList = taskArgumentService.findByBatchId(domainId, batchId);
        this.batchArgumentModelList = batchArgumentService.findByBatchId(domainId, batchId);

//        System.out.println("***************** init batch group relation ****************");
//        for (BatchGroupModel m : batchGroupModelList) {
//            System.out.println("batch id is:" + m.getBatch_id() + ", " + m.getGroup_id());
//        }
//        System.out.println("***************** init group task relation ******************");
//        for (GroupTaskModel m : groupTaskModelList) {
//            System.out.println("group id is: " + m.getGroup_id() + ", " + m.getTask_id());
//        }
//        System.out.println("***************** init task argument relation ***************");
//        for (TaskArgumentModel m : taskArgumentModelList) {
//            System.out.println("task id is:" + m.getTask_id() + ", argument id is:" + m.getArg_id());
//        }
//        System.out.println("***************** init batch argument relation ***************");
//        for (BatchArgumentModel m : batchArgumentModelList) {
//            System.out.println("Batch id is:" + m.getBatch_id() + ", " + m.getArg_id());
//        }
    }

    public void initJobSchedulerService(QuartzConfiguration quartzConfiguration) {
        this.batchId = quartzConfiguration.getBatchId();
        this.domainId = quartzConfiguration.getDomainId();
        this.quartzConfiguration = quartzConfiguration;
        this.scheduler = quartzConfiguration.getSchedulerFactoryBean();
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

        // 初始化任务配置信息
        init();

        try {
            while (scheduler.isRunning()) {
                scheduler.getScheduler().triggerJob(JobKey.jobKey("001"));
                //scheduler.getScheduler().triggerJob(JobKey.jobKey("002"));
                Thread.sleep(1000);
                scheduler.stop();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
