package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
