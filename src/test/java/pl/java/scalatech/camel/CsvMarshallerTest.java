package pl.java.scalatech.camel;

import java.util.List;

import javax.jms.ConnectionFactory;

import lombok.extern.slf4j.Slf4j;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.processor.interceptor.Tracer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;

import pl.java.scalatech.config.CamelConfig;
import pl.java.scalatech.domain.Customer;

@Slf4j
public class CsvMarshallerTest {
    protected CamelContext camelContext;
    protected ProducerTemplate pt;
    protected ConsumerTemplate ct;

    @Test
    public void shouldCreateCSVFile() throws Exception {
        ModelCamelContext context = new DefaultCamelContext();
        Tracer tracer = new Tracer();
        tracer.setLogName("MyTracerLog");
        tracer.getDefaultTraceFormatter().setShowProperties(true);
        tracer.getDefaultTraceFormatter().setShowHeaders(false);
        tracer.getDefaultTraceFormatter().setShowBody(true);
      
        context.addInterceptStrategy(tracer);
        context.addRoutes(new CsvRoute());
        this.camelContext = context;
        this.ct = context.createConsumerTemplate();
        this.pt = context.createProducerTemplate();

        context.start();
        /*for(int i =0 ;i<20;i++) {
        pt.sendBody("seda:start",Customer.builder().firstName("slawek"+i).lastName("borowiec"+i).address("krypska").city("warsaw").state("Poland").zip("04-082").latitude(232.34d).longitude(7542.233).build());
        }*/
        /*List<Customer> customers = Lists.newArrayList(Customer.builder().firstName("agnieszka").lastName("borowiec").address("krypska").city("warsaw").state("Poland").zip("04-082").latitude(72.34d).longitude(92.233).build(),Customer.builder().firstName("kalina").lastName("borowiec").address("krypska").city("warsaw").state("Poland").zip("04-082").latitude(2.34d).longitude(342.83).build());
         pt.sendBody("direct:start",customers);*/
      
        context.setTracing(true);
        Thread.sleep(2000);
        context.stop();
    }
  class CsvRoute extends RouteBuilder {
      BindyCsvDataFormat bindy = new BindyCsvDataFormat("pl.java.scalatech.domain.Customer");
            @Override
            public void configure() throws Exception {
               /* from("seda:start")
                .marshal()
                .bindy(BindyType.Csv,Customer.class)
                .log(LoggingLevel.INFO,"camel","Result marshal : ${body}").to("file://outgoing?fileName=test.csv&fileExist=Append");*/
                from("file://outgoing?noop=true").split(body().tokenize("\n")).streaming().log(LoggingLevel.INFO, "camel", "body is ${body}").unmarshal(bindy).log(LoggingLevel.INFO,"camel","Result unmarshal : ${body}");

            }
        };

    
}
