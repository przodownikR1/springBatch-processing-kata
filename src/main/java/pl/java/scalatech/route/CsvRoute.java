package pl.java.scalatech.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("csv_unmarshal")
public class CsvRoute extends RouteBuilder {


    BindyCsvDataFormat bindy = new BindyCsvDataFormat("pl.java.scalatech.domain.Customer");

    @Override
    public void configure() throws Exception {
        from("file://incoming?noop=true").unmarshal(bindy).log("Result : ${body}");
    }

}
