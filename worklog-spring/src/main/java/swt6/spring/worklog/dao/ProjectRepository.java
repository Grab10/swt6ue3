package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.spring.worklog.domain.Customer;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByCustomer(Customer customer);

    Optional<Project> findByName(String name);
}
