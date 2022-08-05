package com.example.springbatch.domain.jobinstance;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class JobInstanceJobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;

    private final Job jobInstanceJob;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        JobParameters jobParameters = jobParametersBuilder
                .addString("name", "user2").toJobParameters();

        jobLauncher.run(jobInstanceJob, jobParameters);
    }
}
