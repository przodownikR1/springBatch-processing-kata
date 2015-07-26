package pl.java.scalatech.reader;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import pl.java.scalatech.domain.Customer;
@Component
@Profile(value= {"fileCsv","jdbc"})
@StepScope
@Slf4j
@NoArgsConstructor
public class CustomerReader  extends FlatFileItemReader<Customer> {
   
   @Autowired
   public CustomerReader(@Value("#{jobParameters[inputFile]}") String inputFile) {
       log.info("+++          customerReader ...");
       setResource(new FileSystemResource(inputFile));
        setLinesToSkip(1);
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
        tokenizer.setNames(new String[] { "id", "firstName", "lastName", "address", "city", "state", "zip", "longitude", "latitude" });
        mapper.setLineTokenizer(tokenizer);
      
        //BeanWrapperFieldSetMapper<Customer> wrapper = new BeanWrapperFieldSetMapper<>();
        FieldSetMapper<Customer> myFieldMapper = new CustomerFieldSetMapper();
        mapper.setFieldSetMapper(myFieldMapper);
        setLineMapper(mapper);
      
        
    }
   

     class CustomerFieldSetMapper implements FieldSetMapper<Customer> {
        public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
        Customer customer = new Customer();
        customer.setId(fieldSet.readInt("id"));
        customer.setFirstName(fieldSet.readString("firstName"));
        customer.setLastName(fieldSet.readString("lastName"));
        customer.setAddress(fieldSet.readString("address"));
        customer.setCity(fieldSet.readString("city"));
        customer.setState(fieldSet.readString("state"));
        customer.setLongitude(fieldSet.readDouble("longitude"));
        customer.setLatitude(fieldSet.readDouble("latitude"));
        
        return customer;
        }
        }
}