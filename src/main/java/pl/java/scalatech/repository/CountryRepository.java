package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.Country;

public interface CountryRepository extends JpaRepository<Country, String>{

}
