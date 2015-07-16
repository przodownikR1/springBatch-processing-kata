package pl.java.scalatech.tasklet;

import static org.springframework.batch.repeat.RepeatStatus.FINISHED;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class HelloTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(final StepContribution sc, final ChunkContext context) throws Exception {

        log.info("First simple task ..... execute !!! ");
        log.info("+++ StepContribution :  {} ", sc);
        log.info("+++  ChunkContext  :  {}  -> jobName  : {} ", context, context.getStepContext().getJobName());
        log.info("+++  StepContext :  jobParameters :  {} , stepExecution : {} , stepName :  {} ", context.getStepContext().getJobParameters(), context
                .getStepContext().getStepExecution(), context.getStepContext().getStepName());
        ExecutionContext jobExecutionContext = context.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        jobExecutionContext.put("x", "y");
        return FINISHED;
    }
}