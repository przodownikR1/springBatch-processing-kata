package pl.java.scalatech.reader;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import pl.java.scalatech.domain.Customer;

@Slf4j
@Profile(value= {"jpa","dev"})
@Component
@NoArgsConstructor
@StepScope

public class JpaReader extends JpaPagingItemReader<Customer> {

    @Override
    @Resource
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        log.info("setEntityManagerFactory      {}", entityManagerFactory);
        super.setEntityManagerFactory(entityManagerFactory);
    }

    @PostConstruct
    public void post() {
        log.info("!!!!!!!!!!!!!!!! +++          JpaReader ");
    }

    @Autowired
    public JpaReader(EntityManagerFactory entityManagerFactory/* @Value("#{jobParameters['id']}") Long id, @Value("#{jobParameters['page']}") int page */) {
        System.err.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS  " + entityManagerFactory);
        setEntityManagerFactory(entityManagerFactory);
        setQueryString("select c from Customer c where c.id > " + 1);

        setPageSize(10);

    }
}
