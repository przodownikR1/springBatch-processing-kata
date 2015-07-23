package pl.java.scalatech;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.java.scalatech.config.BatchConfig;
import pl.java.scalatech.config.JpaConfig;
import pl.java.scalatech.config.PartiotionJpaConfig;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@SpringApplicationConfiguration(classes = {BatchConfig.class, JpaConfig.class,PartiotionJpaConfig.class })
public class JpaPartitionTest {
    @Autowired
    private JobLauncher jobLauncher;
    
    @Qualifier("airportsJob")
    @Autowired
    private Job airportJob;

    @Qualifier("airportsPartitionJob")
    @Autowired
    private Job airportsPartitionJob;


    @Test
    public void simpleJpaJob() throws Exception {
        Assert.assertEquals(BatchStatus.COMPLETED, jobLauncher.run(airportJob, new JobParameters()).getStatus());
    }
}
