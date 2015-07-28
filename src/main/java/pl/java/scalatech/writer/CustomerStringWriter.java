package pl.java.scalatech.writer;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemWriter;

import pl.java.scalatech.domain.Customer;

@Slf4j
public class CustomerStringWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> items) throws Exception {
        items.stream().forEach(c -> log.info("www {}", c));
    }

}
