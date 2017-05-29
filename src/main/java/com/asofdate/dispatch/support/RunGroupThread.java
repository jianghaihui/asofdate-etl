package com.asofdate.dispatch.support;

import com.asofdate.dispatch.model.GroupTaskModel;
import com.asofdate.dispatch.service.GroupStatusService;
import com.asofdate.dispatch.service.TaskStatusService;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.Map;

/**
 * Created by hzwy23 on 2017/5/29.
 */
public class RunGroupThread extends Thread {
    private Scheduler scheduler;
    private TaskStatusService taskStatus;
    private GroupStatusService groupStatus;
    private String gid;
    private String groupId;

    public RunGroupThread(Scheduler scheduler,
                          TaskStatusService taskStatusService,
                          GroupStatusService groupStatusService,
                          String gid, String groupId) {
        this.scheduler = scheduler;
        this.taskStatus = taskStatusService;
        this.groupStatus = groupStatusService;
        this.gid = gid;
        this.groupId = groupId;
    }

    @Override
    public void run() {
        /*
        * 将任务组设置成运行中
        * 根据任务组中的任务之间的依赖关系
        * 挨个执行这个任务组中的任务
        * 当这个任务组中的所有任务执行完成之后,设置任务组为完成状态
        * */
        while (true) {
            Map<String, GroupTaskModel> taskMap = taskStatus.getRunnableTasks(gid, groupId);

            for (GroupTaskModel mt : taskMap.values()) {
                try {
                    taskStatus.setTaskRunning(join(gid, mt.getUuid()));
                    scheduler.triggerJob(JobKey.jobKey(join(gid, mt.getUuid())));
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }

            if (taskStatus.isGroupCompleted(gid, groupId)) {
                groupStatus.setGroupCompleted(gid);
                break;
            }

            if (taskStatus.isError()){
                groupStatus.setGroupError(gid);
                break;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String join(String... str1) {
        String result = "";
        for (String s : str1) {
            result += s + "__join__";
        }
        return result;
    }
}
