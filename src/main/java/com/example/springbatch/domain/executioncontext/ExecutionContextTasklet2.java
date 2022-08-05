package com.example.springbatch.domain.executioncontext;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

public class ExecutionContextTasklet2 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("Step2 has been executed");

        ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
        ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

        ExecutionContext jobExecutionContext2 = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        ExecutionContext stepExecutionContext2 = chunkContext.getStepContext().getStepExecution().getExecutionContext();

        System.out.println("jobExecutionContext = " + jobExecutionContext.get("jobName"));
        System.out.println("stepExecutionContext = " + stepExecutionContext.get("stepName"));

        System.out.println("jobExecutionContext2 = " + jobExecutionContext2.get("jobName"));
        System.out.println("stepExecutionContext2 = " + stepExecutionContext2.get("stepName"));

        String stepName = chunkContext.getStepContext().getStepName();
        if (stepExecutionContext2.get("stepName") == null) {
            stepExecutionContext2.put("stepName", stepName);
        }

        return RepeatStatus.FINISHED;
    }
}
