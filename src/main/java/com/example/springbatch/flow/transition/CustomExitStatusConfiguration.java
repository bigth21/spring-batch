package com.example.springbatch.flow.transition;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
public class CustomExitStatusConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("customExitStatus.job")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .on(ExitStatus.FAILED.getExitCode()).to(step2())
                .on("PASS").stop()
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    contribution.setExitStatus(ExitStatus.FAILED);
                    return null;
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> null)
                .listener(new PassCheckingListener()).build();
    }

    static class PassCheckingListener implements StepExecutionListener {

        @Override
        public void beforeStep(StepExecution stepExecution) {

        }

        @Override
        public ExitStatus afterStep(StepExecution stepExecution) {
            var exitCode = stepExecution.getExitStatus();
            if (!exitCode.equals(ExitStatus.FAILED)) {
                return new ExitStatus("PASS");
            }
            return null;
        }
    }
}
