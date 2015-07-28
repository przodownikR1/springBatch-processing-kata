package pl.java.scalatech.listener;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.ItemReadListener;

import pl.java.scalatech.domain.Customer;

@Slf4j
public class ItemStepReaderListener implements ItemReadListener<Customer> {

    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(Customer item) {
        log.info("rrr$$$  ItemReadListener - afterRead  {}", item);
    }

    @Override
    public void onReadError(Exception ex) {

    }

}