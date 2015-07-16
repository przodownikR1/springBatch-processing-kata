package pl.java.scalatech.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import pl.java.scalatech.tasklet.FirstTasklet;
import pl.java.scalatech.tasklet.HelloTasklet;

@Configuration
@Slf4j
public class FirstTaskletConfig {
    @Autowired
    private JobBuilderFactory jobs;
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private StepBuilderFactory stepBuilders;

    @Bean
    public Job job(JobExecutionListener listener) {
        return jobs.get("HelloJob").listener(listener).start(step()).next(step1()).build();
    }

    @Bean
    public Step step() {
        return stepBuilders.get("step").tasklet(helloTasklet()).build();
    }

    public Step step1() {
        return stepBuilders.get("step1").tasklet(firstTasklet()).build();
    }

    @Bean
    public Tasklet helloTasklet() {
        return new HelloTasklet();
    }

    @Bean
    public Tasklet firstTasklet() {
        return new FirstTasklet();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }
}
