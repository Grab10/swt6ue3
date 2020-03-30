package swt6.spring.worklog.logic.interf;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.domain.Customer;
import swt6.spring.worklog.domain.Project;

import java.util.List;
import java.util.Optional;


public interface CustomerLogic {
    void AddCustomerToProject(Project project, Customer customer);

    void RemoveCustomerFromProject(Project project, Customer customer);

    Customer syncCustomer(Customer customer);

    List<Customer> findAllCustomers();

    Optional<Customer> findByLastname(String lastname);
}
