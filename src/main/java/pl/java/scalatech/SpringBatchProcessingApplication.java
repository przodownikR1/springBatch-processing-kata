package pl.java.scalatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;

@SpringBootApplication(exclude = { HypermediaAutoConfiguration.class, MultipartAutoConfiguration.class })
public class SpringBatchProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchProcessingApplication.class, args);
    }
}
