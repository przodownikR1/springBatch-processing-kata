package pl.java.scalatech.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "airports")
public class Airport {
    @Id
    private Long id;
    
    private String airPortName;
    
    private String city;
    
    private String country;
    
    private String shortcut;
    
    private String arName;
    
    private BigDecimal lat;
    
    private BigDecimal lon;
    
    private BigDecimal alt;
    
    private long port;
    
    private String sk;
    
    private String region;
   
}