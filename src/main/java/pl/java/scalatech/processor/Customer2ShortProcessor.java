package pl.java.scalatech.processor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemProcessor;

import pl.java.scalatech.domain.Customer;
import pl.java.scalatech.domain.ShortCustomer;

@Slf4j
public class Customer2ShortProcessor implements ItemProcessor<Customer, ShortCustomer> {

    @Override
    public ShortCustomer process(Customer item) throws Exception {
        log.info("###  {}", item);
        return ShortCustomer.builder().lastName(item.getLastName()).city(item.getCity()).build();
    }

}
