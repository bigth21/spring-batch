package com.example.springbatch.flow.transition;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlowConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJob() {
        return jobBuilderFactory.get("flow.job")
                .start(flow1())
                .on("COMPLETED").to(flow2())
                .end()
                .build();
    }

    @Bean
    public Flow flow1() {
        var builder = new FlowBuilder<Flow>("flow1");
        return builder.start(step1())
                .next(step2())
                .end();
    }

    @Bean
    public Flow flow2() {
        var builder = new FlowBuilder<Flow>("flow2");
        return builder.start(flow3())
                .next(step5())
                .next(step6())
                .end();
    }

    @Bean
    public Flow flow3() {
        var builder = new FlowBuilder<Flow>("flow3");
        return builder.start(step3())
                .next(step4())
                .end();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step1 has been executed");
                    return RepeatStatus.FINISHED;
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

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step4 has been executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step5 has been executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step6() {
        return stepBuilderFactory.get("step6")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step6 has been executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
