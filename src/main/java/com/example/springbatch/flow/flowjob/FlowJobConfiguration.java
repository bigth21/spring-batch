package com.example.springbatch.flow.flowjob;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlowJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("flowJob.job")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .on(ExitStatus.COMPLETED.getExitCode()).to(step3())
                .from(step1())
                .on(ExitStatus.FAILED.getExitCode()).to(step2())
                .end()
                .build();

    }

    // Step must be registered as bean to be used in flowJob process
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step1 has been executed");
                    throw new RuntimeException();
//                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step2 has been executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step3 has been executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
