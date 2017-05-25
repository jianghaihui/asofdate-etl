package com.asofdate.dispatch.support;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by hzwy23 on 2017/5/21.
 */
public class QuartzJobLauncher extends QuartzJobBean {

    private JobLauncher jobLauncher;
    private JobRegistry jobRegistry;
    private JobExplorer jobExplorer;
    private JobOperator jobOperator;
    private String jobName;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public JobOperator getJobOperator() {
        return jobOperator;
    }

    public void setJobOperator(JobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }

    public JobExplorer getJobExplorer() {
        return jobExplorer;
    }

    public void setJobExplorer(JobExplorer jobExplorer) {
        this.jobExplorer = jobExplorer;
    }

    public JobRegistry getJobRegistry() {
        return jobRegistry;
    }

    public void setJobRegistry(JobRegistry jobRegistry) {
        this.jobRegistry = jobRegistry;
    }

    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        try {
            Job job = jobRegistry.getJob(jobName);
            JobParameters jobParameters = new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis()).addString("as_of_date", "2017-01-01").toJobParameters();
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            if ("COMPLETED".equals(jobExecution.getStatus().toString())) {
                jobRegistry.unregister(jobName);
            }

        } catch (NoSuchJobException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        }
    }
}
