package com.asofdate.etl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hzwy23 on 2017/5/20.
 */
@Configuration
public class EtlBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public CustomItemReader reader() {
        return new CustomItemReader();
    }

    @Bean
    public CustomItemWriter writer() {
        return new CustomItemWriter();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Object, Object>chunk(10)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public Job testJob(Step step1) throws Exception {
        return jobBuilderFactory.get("testJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

}