package pl.java.scalatech.tasklet;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class SecondTasklet implements Tasklet {
    @Setter
    @Getter
    private String status;
    @Setter
    private Integer slept;

    @Override
    public RepeatStatus execute(StepContribution arg0, ChunkContext context) throws Exception {

        String name = (String) context.getStepContext().getJobParameters().get("name");
        ExecutionContext jobExecutionContext = context.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        if (slept != null) {
            Thread.sleep(slept);
        }

        log.info("+++ Second  Tasklet  {} : name : {} ", status, name);
        return RepeatStatus.FINISHED;
    }

}