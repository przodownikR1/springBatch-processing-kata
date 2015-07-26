package pl.java.scalatech.reader;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import pl.java.scalatech.config.JpaConfig;
import pl.java.scalatech.domain.Customer;

@Component
@Profile(value= {"jpa","dev"})
@StepScope
@Slf4j
@NoArgsConstructor
public class JpaReader extends JpaPagingItemReader<Customer>{
    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private EntityManager em;
    
    @PostConstruct
    public void post() {
        log.info("!!!!!!!!!!!!!!!!           JpaReader {}  {}",emf,em);
    }
        
    @Autowired
    public JpaReader(EntityManagerFactory emf,EntityManager em,@Value("#{jobParameters['id']}") Long id,@Value("#{jobParameters['page']}") int page) {
        System.err.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
        log.info("+++   {}  ->   {}",emf,em);
        setEntityManagerFactory(emf);

        setQueryString("select c from Customer c where c.id > " + id);

        setPageSize(page);

           
    }
    
}
