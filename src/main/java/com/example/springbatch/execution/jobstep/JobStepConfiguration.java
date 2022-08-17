package com.example.springbatch.execution.jobstep;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.batch.core.step.job.JobParametersExtractor;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobStepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;

    @Bean
    public Job parentJob() {
        return jobBuilderFactory.get("jobStep.job")
                .start(jobStep())
                .build();
    }

    public Step jobStep() {
        return stepBuilderFactory.get("jobStep")
                .job(childJob())
                .launcher(jobLauncher)
                .parametersExtractor(jobParametersExtractor())
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        stepExecution.getExecutionContext().putString("name", "user1");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return null;
                    }
                })
                .build();
    }

    private DefaultJobParametersExtractor jobParametersExtractor() {
        var extractor = new DefaultJobParametersExtractor();
        extractor.setKeys(new String[]{"name"});
        return extractor;
    }

    public Job childJob() {
        return jobBuilderFactory.get("jobStepChild.job")
                .start(step())
                .build();
    }

    public Step step() {
        return stepBuilderFactory.get("step")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Step has been executed");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}
