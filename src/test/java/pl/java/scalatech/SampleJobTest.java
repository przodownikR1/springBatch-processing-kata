package pl.java.scalatech;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.Matchers.*;
import pl.java.scalatech.config.BatchConfig;
import pl.java.scalatech.config.FirstTaskletConfig;
import pl.java.scalatech.config.JpaConfig;
import pl.java.scalatech.job.SampleJob;

import com.google.common.collect.Maps;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles= {"sampleJob","dev"})
@SpringApplicationConfiguration(classes = { JpaConfig.class, BatchConfig.class, SampleJob.class })
public class SampleJobTest {
 
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Value("classpath:org/springframework/batch/core/schema-mysql.sql")
    private Resource schemaScript;

    @Value("classpath:org/springframework/batch/core/schema-drop-mysql.sql")
    private Resource dropScript;

    @Test
    public void shouldScriptLoad() {
        assertThat(schemaScript.exists()).isTrue();
        assertThat(dropScript.exists()).isTrue();
    }

    @Test
    public void shouldTaskletThrowException() {
        exception.expect(AssertionError.class);
        exception.expectMessage(containsString("This exception was expected"));
        try {
            Map<String, JobParameter> params = Maps.newHashMap();
            params.put("test", new JobParameter("przodownik"));
            params.put("name", new JobParameter("borowiec"));
            params.put("fail", new JobParameter("true"));
            JobExecution execution = jobLauncher.run(job, new JobParameters(params));
            log.info("Exit Status :  {}", execution.getStatus());
            assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void shouldTaskletEndWithCompletedStatus() {
     
        try {
            Map<String, JobParameter> params = Maps.newHashMap();
            params.put("test", new JobParameter("przodownik"));
            params.put("name", new JobParameter("borowiec"));
            params.put("fail", new JobParameter("false"));
            JobExecution execution = jobLauncher.run(job, new JobParameters(params));
            log.info("Exit Status :  {}", execution.getStatus());
            assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}