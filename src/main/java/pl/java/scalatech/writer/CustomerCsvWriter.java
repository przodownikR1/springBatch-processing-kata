package pl.java.scalatech.writer;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import pl.java.scalatech.domain.Customer;
@StepScope
@Component
@Profile(value= {"fileCsv","jpa"})
@Slf4j
@NoArgsConstructor
public class CustomerCsvWriter extends FlatFileItemWriter<Customer>{
    
    
    @Autowired
    public CustomerCsvWriter(@Value("#{jobParameters[outputFile]}") String outputFile) {
        log.info("+++         customerCsvWriter ..");
        setResource(new FileSystemResource(outputFile));
        DelimitedLineAggregator<Customer> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<Customer> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[]{"firstName","lastName","address","city","longitude","latitude"});
        aggregator.setFieldExtractor(extractor);
        setLineAggregator(aggregator);
        
    }

}