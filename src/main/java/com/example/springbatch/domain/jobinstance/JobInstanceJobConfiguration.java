package com.example.springbatch.domain.jobinstance;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 3. Spring Batch domain understanding
 *  - Job Instance
 */
//@Configuration
@RequiredArgsConstructor
public class JobInstanceJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean("jobInstance.job")
    public Job job() {
        return jobBuilderFactory.get("jobInstance.job")
                .start(step1())
                .next(step2()).build();
    }

    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step1 has been executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step2 has been executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
