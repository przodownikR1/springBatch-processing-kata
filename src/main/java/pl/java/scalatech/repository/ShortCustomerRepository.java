package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.ShortCustomer;

public interface ShortCustomerRepository extends JpaRepository<ShortCustomer, Long> {

}
