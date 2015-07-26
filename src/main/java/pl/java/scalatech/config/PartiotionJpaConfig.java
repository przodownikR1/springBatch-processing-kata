package pl.java.scalatech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("partition")
@ImportResource("classpath:jpaPartitionJob.xml")
public class PartiotionJpaConfig {

}
