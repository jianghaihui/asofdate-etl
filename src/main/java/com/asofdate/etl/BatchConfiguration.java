package com.asofdate.etl;

import com.asofdate.etl.bak.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hzwy23 on 2017/5/20.
 */
@Configuration
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    @Qualifier("stepOne")
    public Step stepOne() {
        return stepBuilderFactory.get("stepOne")
                .<Object, Object>chunk(10)
                .reader(new CustomItemReader())
                .writer(new CustomItemWriter())
                .build();
    }

    @Bean
    @Qualifier("stepTwo")
    public Step stepTwo() {
        return stepBuilderFactory.get("stepTwo")
                .<Object, Object>chunk(10)
                .reader(new CustomItemReader())
                .writer(new CustomItemWriter())
                .build();
    }

    @Bean
    public Job job(JobCompletionNotificationListener jobCompletionNotificationListener) throws Exception {
        return jobBuilderFactory.get("defaultJobAsOfDate")
                .incrementer(new RunIdIncrementer())
                .flow(stepOne())
                .next(stepTwo())
                .end()
                .build();
    }

}