package pl.java.scalatech.listener;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.ItemWriteListener;

import pl.java.scalatech.domain.Customer;

@Slf4j
public class ItemStepWriterListener implements ItemWriteListener<Customer> {

    @Override
    public void beforeWrite(List<? extends Customer> items) {
        log.info("$$$www  ItemWriteListener - beforeWrite {}", items);
    }

    @Override
    public void afterWrite(List<? extends Customer> items) {
        log.info("$$$www  ItemWriteListener - afterWrite {}", items);
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Customer> items) {
        log.info("$$$www  ItemWriteListener - onWriteError {} ", items);
    }

}