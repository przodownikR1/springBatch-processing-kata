package pl.java.scalatech.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement
@XmlType
@Entity
@ToString(exclude = { "address", "state", "zip", "longitude", "latitude" })
@CsvRecord(separator = ",", skipFirstLine = false)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @DataField(pos = 1)
    @XmlElement
    private String firstName;
    @DataField(pos = 2)
    @XmlElement
    private String lastName;
    @DataField(pos = 3)
    @XmlElement
    private String address;
    @DataField(pos = 4)
    @XmlElement
    private String city;
    @DataField(pos = 5)
    @XmlElement
    private String state;
    @DataField(pos = 6)
    @XmlElement
    private String zip;
    @XmlElement
    @DataField(pos = 7)
    private Double longitude;
    @XmlElement
    @DataField(pos = 8)
    private Double latitude;

}