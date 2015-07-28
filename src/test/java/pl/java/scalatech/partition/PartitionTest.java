package pl.java.scalatech.partition;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Maps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:partitionJob.xml" })
@ActiveProfiles(value = { "dev" })
@Slf4j
public class PartitionTest {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job partitionJob;

    @Autowired
    private DataSource dataSource;

    @Test
    public void shouldTaskletPrintSomething() throws SQLException {
        log.info(" +++   db driver :  {}", dataSource.getConnection().getMetaData().getDriverName());

        try {
            Map<String, JobParameter> params = Maps.newHashMap();
            params.put("time", new JobParameter(new Date()));
            JobExecution execution = jobLauncher.run(partitionJob, new JobParameters(params));
            log.info("Exit Status :  {}", execution.getExitStatus());
            log.info("Status : {}", execution.getStatus());
            log.info("Exit Status : {}", execution.getAllFailureExceptions());
            Assert.assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
