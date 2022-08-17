package com.example.springbatch.step.taskletstep;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

//@Configuration
@RequiredArgsConstructor
public class TaskletStepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("taskletStep.job")
                .start(taskBasedStep())
                .build();
    }

    public Step taskBasedStep() {
        return stepBuilderFactory.get("taskBasedStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Step1 has been executed");
                        return RepeatStatus.FINISHED; // Same with null value
                    }
                })
                .allowStartIfComplete(true) // Irrespective of success or failure, restart will always be executed;
                .build();
    }
    public Step chunkBasedStep() {
        return stepBuilderFactory.get("chunkBasedStep")
                .<String, String>chunk(10)
                .reader(new ListItemReader<>(Arrays.asList("item1", "item2", "item3", "item4", "item5")))
                .processor((ItemProcessor<String, String>) item -> item.toUpperCase())
                .writer(items -> items.forEach(x -> System.out.println(x)))
                .build();
    }
}
