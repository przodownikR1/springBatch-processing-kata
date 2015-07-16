package pl.java.scalatech.job;

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
public class SampleJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public FailableTasklet tasklet(@Value("#{jobParameters[fail]}") Boolean failable) {
        if (failable != null) { return new FailableTasklet(failable); }
        return new FailableTasklet(false);
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step").tasklet(tasklet(null)).build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job").start(step()).build();
    }

    public static class FailableTasklet implements Tasklet {

        private final boolean fail;

        public FailableTasklet(boolean fail) {
            this.fail = fail;
        }

        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
            System.out.println("Tasklet was executed");

            if (fail) { throw new RuntimeException("This exception was expected"); }
            return RepeatStatus.FINISHED;
        }
    }
}