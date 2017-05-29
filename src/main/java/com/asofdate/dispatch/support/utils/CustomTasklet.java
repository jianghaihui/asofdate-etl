package com.asofdate.dispatch.support.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Created by hzwy23 on 2017/5/29.
 */
public class CustomTasklet implements Tasklet {
    private final Logger logger = LoggerFactory.getLogger(CustomTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        for (String m : chunkContext.getStepContext().getJobParameters().keySet()) {
            System.out.println(m);
        }
        logger.info("as_of_date is:" + chunkContext.getStepContext().getJobParameters().get("as_of_date"));
        logger.info("set exit status is FAILED.");
        stepContribution.setExitStatus(ExitStatus.FAILED);
        //chunkContext.getStepContext().getStepExecution().setExitStatus(ExitStatus.FAILED);

        return RepeatStatus.FINISHED;
    }
}
