package pl.java.scalatech.job.jpa;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import pl.java.scalatech.config.JpaConfig;
import pl.java.scalatech.domain.Customer;
import pl.java.scalatech.reader.JpaReader;
import pl.java.scalatech.writer.CustomerCsvWriter;

@Configuration
@Profile(value = { "jpa" })
@Slf4j
@ComponentScan(basePackageClasses = { JpaConfig.class, JpaReader.class, CustomerCsvWriter.class })
public class JpaReaderJob extends JpaPagingItemReader<Customer> {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JpaReader jpaReader;

    @Autowired
    private CustomerCsvWriter customerCsvWriter;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void post() {
        log.info("!!!!!!!!!!!!!!!!+           jpaReaderJob {}", entityManagerFactory);
    }

    @Bean
    @DependsOn("entityManagerFactory")
    protected Job job() throws Exception {
        return jobBuilderFactory.get("jobJPA").start(step1()).build();
    }

    @Bean
    @StepScope
    protected Step step1() throws Exception {
        return stepBuilderFactory.get("readStep").<Customer, Customer> chunk(10).reader(jpaReader).writer(customerCsvWriter).build();
    }

    /*
     * @Bean
     * public Flow buildFlow() throws Exception {
     * FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("simpleJpa");
     * flowBuilder.start(step1());
     * return flowBuilder.build();
     * }
     */

}
