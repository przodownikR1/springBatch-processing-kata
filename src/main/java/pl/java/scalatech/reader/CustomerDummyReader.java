package pl.java.scalatech.reader;

import java.util.List;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.assertj.core.util.Lists;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import pl.java.scalatech.domain.Customer;

@Slf4j
public class CustomerDummyReader implements ItemReader<Customer> {
    List<Customer> custs = Lists.newArrayList();

    @PostConstruct
    public void init() {
        for (int i = 0; i < 40; i++) {
            custs.add(Customer.builder().id(i).firstName("s" + i).lastName("b" + i).city("c" + i).build());
        }
    }

    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Customer cust = !custs.isEmpty() ? custs.remove(0) : null;
        log.info("rrr : {}", cust);
        return cust;
    }

}
