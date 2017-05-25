package com.asofdate.dispatch.support;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;

import java.io.Serializable;

/**
 * Created by hzwy23 on 2017/5/25.
 */
public class JobRun implements Runnable, Serializable {
    private JobLauncher jobLauncher;
    private JobRegistry jobRegistry;
    private JobExplorer jobExplorer;
    private String jobName;

    public JobRun(JobLauncher jobLauncher, JobRegistry jobRegistry, JobExplorer jobExplorer, String jobName) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
        this.jobExplorer = jobExplorer;
        this.jobName = jobName;
    }

    @Override
    public void run() {

    }
}
