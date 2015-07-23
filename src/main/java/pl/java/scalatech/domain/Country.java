package pl.java.scalatech.domain;

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
@Entity(name = "countries")
public class Country {
    @Id
    
    private String countryName;
    
    private String shortcutOne;
    
    private String shortcutTwo;
    
    private String code;
}
