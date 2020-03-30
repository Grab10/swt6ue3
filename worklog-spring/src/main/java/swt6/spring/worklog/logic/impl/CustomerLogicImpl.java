package swt6.spring.worklog.logic.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.CustomerRepository;
import swt6.spring.worklog.dao.ProjectRepository;
import swt6.spring.worklog.domain.Customer;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.logic.interf.CustomerLogic;

import java.util.List;
import java.util.Optional;

@Service("customerLogic")
@Primary
@Transactional
public class CustomerLogicImpl implements CustomerLogic {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    @Transactional
    public void AddCustomerToProject(Project project, Customer customer) {
        project = projectRepository.saveAndFlush(project);
        customer = customerRepository.saveAndFlush(customer);

        customer.getProjects().add(project);
        if (project.getCustomer() != null) {
            project.getCustomer().getProjects().remove(project);
        }
        project.setCustomer(customer);

        customerRepository.saveAndFlush(customer);
        projectRepository.saveAndFlush(project);
    }

    @Override
    public void RemoveCustomerFromProject(Project project, Customer customer) {
        project = projectRepository.saveAndFlush(project);
        customer = customerRepository.saveAndFlush(customer);

        customer.getProjects().remove(project);
        project.setCustomer(null);

        customerRepository.saveAndFlush(customer);
        projectRepository.saveAndFlush(project);
    }

    @Override
    public Customer syncCustomer(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findByLastname(String lastname) {
        return customerRepository.findByLastName(lastname);
    }

}
