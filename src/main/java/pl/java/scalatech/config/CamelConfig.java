package pl.java.scalatech.config;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@Slf4j
@ComponentScan(basePackages = { "pl.java.scalatech.route", "pl.java.scalatech.processor" }, includeFilters = { @Filter(Component.class) })
@PropertySource("classpath:camel.properties")
public class CamelConfig implements CamelContextConfiguration {

    @Autowired
    private CamelContext camelContext;

    @Value("${camel.traceFlag}")
    private Boolean traceFlag;

    @PostConstruct
    public void init() {
        camelContext.setTracing(traceFlag);

    }

    @Override
    public void beforeApplicationStart(CamelContext camelContext) {
        log.info("+++  beforeApplicationStart");
        camelContext.setUseMDCLogging(true);
        camelContext.setUseBreadcrumb(true);

    }

}
