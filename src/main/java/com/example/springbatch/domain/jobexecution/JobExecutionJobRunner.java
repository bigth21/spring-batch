package com.example.springbatch.domain.jobexecution;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class JobExecutionJobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;

    private final Job jobExecutionJob;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        JobParameters jobParameters = jobParametersBuilder
                .addString("name", "user4").toJobParameters();

        jobLauncher.run(jobExecutionJob, jobParameters);
    }
}
