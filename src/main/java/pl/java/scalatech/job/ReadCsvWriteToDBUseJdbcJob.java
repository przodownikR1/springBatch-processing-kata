package pl.java.scalatech.job;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import pl.java.scalatech.domain.Customer;
import pl.java.scalatech.reader.CustomerReader;
import pl.java.scalatech.writer.CustomerJdbcWriter;

@Configuration
@Component
@Profile(value = { "fileCsv", "jdbc" })
@Slf4j
@ComponentScan(basePackageClasses = { CustomerReader.class, CustomerJdbcWriter.class })
public class ReadCsvWriteToDBUseJdbcJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CustomerReader customerReader;

    @Autowired
    private CustomerJdbcWriter customerJdbcWriter;

    @Bean
    protected Job job() throws Exception {
        return jobBuilderFactory.get("jobJdbc").start(step1()).build();
    }

    @StepScope
    protected Step step1() throws Exception {
        return stepBuilderFactory.get("readStep").<Customer, Customer> chunk(10).reader(customerReader).writer(customerJdbcWriter).build();
    }
}
