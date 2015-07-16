package pl.java.scalatech.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "pl.java.scalatech.repository")
@EntityScan(basePackages = "pl.java.scalatech.domain")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableTransactionManagement
@ComponentScan(basePackages = { "pl.java.scalatech.service", "pl.java.scalatech.repository" })
@Slf4j
public class JpaConfig {
    @Value("${batch.jdbc.driver}")
    private String driverDB;
    @Value("${batch.jdbc.url}")
    private String urlDB;
    @Value("${batch.jdbc.user}")
    private String userDB;
    @Value("${batch.jdbc.password}")
    private String passwdDB;

    @PostConstruct
    public void init() {
        log.debug("+++ init - > driverDb {}", driverDB);
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        log.debug("+++ primary DataSource -> Batch config <- {} : {}", driverDB, urlDB);
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverDB);
        ds.setUrl(urlDB);
        ds.setUsername(userDB);
        ds.setPassword(passwdDB);
        return ds;
    }

    @Configuration
    @PropertySource("classpath:batch-test.properties")
    @Profile("test")
    static class PropertiesLoaderForH2 {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            log.info("+++         propertySource H2-> prop profile launch");
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Bean
        public EmbeddedDatabase springPropertyPopulatorTestDb() {
            EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
            EmbeddedDatabase embeddedDatabase = embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.H2).build();
            return embeddedDatabase;
        }
    }

    @Configuration
    @PropertySource("classpath:batch-dev.properties")
    @Profile("dev")
    static class PropertiesLoaderForMysql {

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            log.info("+++               propertySource Mysql -> prop profile launch");
            return new PropertySourcesPlaceholderConfigurer();
        }
    }
}
