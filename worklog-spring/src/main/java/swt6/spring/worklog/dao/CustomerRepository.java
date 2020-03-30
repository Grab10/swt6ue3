package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.spring.worklog.domain.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByLastName(String lastname);
}
