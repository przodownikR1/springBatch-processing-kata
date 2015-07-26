package pl.java.scalatech;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.assertj.core.api.Assertions;
import org.hibernate.hql.internal.ast.tree.IsNotNullLogicOperatorNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.java.scalatech.config.JpaConfig;
import pl.java.scalatech.domain.Airport;
import pl.java.scalatech.domain.Country;
import pl.java.scalatech.repository.AirportRepository;
import pl.java.scalatech.repository.CountryRepository;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@Transactional
@SpringApplicationConfiguration(classes = { JpaConfig.class })
public class JpaDataSourceTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private AirportRepository airportRepository;
    
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private EntityManager entityManager;
    
    @Test
    public void shouldDatabaseSourceCreate() {
        Assertions.assertThat(entityManager).isNotNull();
        Assertions.assertThat(emf).isNotNull();
        Assertions.assertThat(dataSource).isNotNull();
        Assertions.assertThat(airportRepository).isNotNull();
        Assertions.assertThat(countryRepository).isNotNull();
    }
    @Test
    public void shouldPersistCountry() {
        Country country = new Country("Poland", "PL", "PL", "U");
        countryRepository.save(country);
        Assertions.assertThat(countryRepository.count()).isEqualTo(1);
    }
    
    @Test
    public void shouldPersistAirport() {
        //679,"Okecie","Warsaw","Poland","WAW","EPWA",52.16575,20.967122,362,1,"E","Europe/Warsaw"
        
     Airport airport = Airport.builder().id(679l).airPortName("Okecie").city("Warsaw").country("Poland").shortcut("WAW").
             arName("EPWA").lat(BigDecimal.valueOf(52.16575)).lon(BigDecimal.valueOf(20.967122)).alt(BigDecimal.valueOf(362)).port(1).
             sk("E").region("Europe/Warsaw").build();
     airportRepository.save(airport);
     Assertions.assertThat(airportRepository.count()).isEqualTo(1);
             
    }
}
