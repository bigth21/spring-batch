package com.example.springbatch.job.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

//@Configuration
@RequiredArgsConstructor
public class ValidatorConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("validator.job")
                .validator(new DefaultJobParametersValidator(new String[]{"name", "date"}, new String[]{"count"}))
//                .validator(new CustomJobParametersValidator())
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.FAILED);
                    contribution.setExitStatus(ExitStatus.STOPPED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
