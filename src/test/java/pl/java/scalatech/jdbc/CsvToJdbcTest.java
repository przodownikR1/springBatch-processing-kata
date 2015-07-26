package pl.java.scalatech.jdbc;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.BatchConfig;
import pl.java.scalatech.config.JpaConfig;
import pl.java.scalatech.job.ReadCsvWriteToDBUseJdbcJob;

import com.google.common.collect.Maps;
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles= {"fileCsv","jdbc","dev"})
@SpringApplicationConfiguration(classes = { JpaConfig.class, BatchConfig.class, ReadCsvWriteToDBUseJdbcJob.class })
public class CsvToJdbcTest {    
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Test
    public void shouldCsvReadAndWriteException() {
     
        try {
            Map<String, JobParameter> params = Maps.newHashMap();
            params.put("inputFile", new JobParameter("custs.csv"));
            params.put("test", new JobParameter("przodownik"));
            JobExecution execution = jobLauncher.run(job, new JobParameters(params));
            log.info("Exit Status :  {}", execution.getStatus());
            assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}