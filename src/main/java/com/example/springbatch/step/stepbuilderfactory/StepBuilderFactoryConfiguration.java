package com.example.springbatch.step.stepbuilderfactory;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

//@Configuration
@RequiredArgsConstructor
public class StepBuilderFactoryConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("stepBuilderFactory.job")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .next(step2())
                .next(step3())
                .build();

    }

    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> { // Task-base
                    System.out.println("Step1 has been executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<String, String>chunk(3) // Chunk-base
                .reader(() -> null)
                .processor((ItemProcessor<String, String>) item -> null)
                .writer(items -> {})
                .build();
    }

    public Step step3() {
        return stepBuilderFactory.get("step3")
                .partitioner(step1()) // Divide step & execute on multi-threads
                .gridSize(2)
                .build();
    }

    public Step step4() {
        return stepBuilderFactory.get("step4")
                .job(job())
                .build();
    }

    public Step step5() {
        return stepBuilderFactory.get("step5")
                .flow(flow())
                .build();
    }

    public Job job() {
        return this.jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder.start(step2()).end();
        return flowBuilder.build();
    }
}
