package pl.java.scalatech.processor;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pl.java.scalatech.domain.Customer;

@Component("customerProcessor")
@Scope(value = "step")
@Slf4j
public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

    @Value("#{stepExecutionContext[name]}")
    @Setter
    private String threadName;

    @Override
    public Customer process(Customer item) throws Exception {
        log.info("$$$ threadName {} , processing id : {}", threadName, item.getId());
        return item;
    }

}
