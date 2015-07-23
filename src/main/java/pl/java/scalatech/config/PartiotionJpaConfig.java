package pl.java.scalatech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:jpaPartitionJob.xml")
public class PartiotionJpaConfig {

}
