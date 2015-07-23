package pl.java.scalatech.job;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Slf4j
public class SampleJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope //check !!! 
    public FailTasklet tasklet(@Value("#{jobParameters[fail]}") String failable) {
        log.info("+++   invoke tasklet with arg = {}",failable);
        if (failable != null) { return new FailTasklet(failable); }
        return new FailTasklet("false");
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step").tasklet(tasklet(null)).build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job").start(step()).build();
    }

    public static class FailTasklet implements Tasklet {
        private final boolean fail;
        public FailTasklet(String fail) {
            this.fail = Boolean.parseBoolean(fail);
        }

        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
            log.info("Tasklet was executed");
            if (fail) { throw new RuntimeException("This exception was expected"); }
            return RepeatStatus.FINISHED;
        }
    }
}