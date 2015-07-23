package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.Airport;

public interface AirportRepository extends JpaRepository<Airport, String>{

}
