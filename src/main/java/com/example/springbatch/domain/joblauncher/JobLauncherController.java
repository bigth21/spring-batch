//package com.example.springbatch.domain.joblauncher;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.JobParametersInvalidException;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.launch.support.SimpleJobLauncher;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//
//@RestController
//@RequiredArgsConstructor
//public class JobLauncherController {
//
//    private final Job job;
//    private final JobLauncher jobLauncher;
//    private final BasicBatchConfigurer basicBatchConfigurer;
//
//    @PostMapping("/batch")
//    public String launchJob(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("id", member.getId())
//                .addDate("date", new Date())
//                .toJobParameters();
//
//        return jobLauncher.run(job, jobParameters).getExitStatus().getExitCode();
//    }
//
//    @PostMapping("/async-batch")
//    public String launchAsyncBatch(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("id", member.getId())
//                .addDate("date", new Date())
//                .toJobParameters();
//
//        SimpleJobLauncher jobLauncherForAsync = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher(); // This is real object.
////        SimpleJobLauncher jobLauncherForAsync = (SimpleJobLauncher) jobLauncher; // Hear is type casting exception because instance of JobLauncher autowired(injected by spring) is proxy object.
//        jobLauncherForAsync.setTaskExecutor(new SimpleAsyncTaskExecutor());
//
//        return jobLauncherForAsync.run(job, jobParameters).getExitStatus().getExitCode();
//    }
//}
