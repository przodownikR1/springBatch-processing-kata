package pl.java.scalatech.writer;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import pl.java.scalatech.domain.Customer;
@Component
@Profile("jdbc")
public class CustomerJdbcWriter extends JdbcBatchItemWriter<Customer>  {
   
        @Autowired
        public void setPeopleDataSource(DataSource dataSource) {
            setDataSource(dataSource);
        }

        public CustomerJdbcWriter() {
            setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Customer>());
            setSql("INSERT INTO person (firstName,lastName,city) VALUES (:firstName, :lastName, :city)");
        }
    }

