package pl.java.scalatech.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import pl.java.scalatech.domain.Airport;

public class AirportFieldSetMapper implements FieldSetMapper<Airport> {

    public Airport mapFieldSet(FieldSet fieldSet) throws BindException {

        Airport airport = new Airport();
        airport.setId(fieldSet.readLong("ID"));
        airport.setAirPortName(fieldSet.readString("AIRPORTNAME"));
        airport.setCity(fieldSet.readString("CITY"));
        airport.setCountry(fieldSet.readString("COUNTRY"));
        airport.setLat(fieldSet.readBigDecimal("LAT"));
        airport.setLon(fieldSet.readBigDecimal("LON"));
        airport.setLon(fieldSet.readBigDecimal("REGION"));
        
        return airport;
    }

}