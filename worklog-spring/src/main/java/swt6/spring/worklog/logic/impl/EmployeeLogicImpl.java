package swt6.spring.worklog.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.logic.interf.EmployeeLogic;

import java.util.List;
import java.util.Optional;

@Service("employeeLogic")
@Primary
@Transactional
public class EmployeeLogicImpl implements EmployeeLogic {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> findAllEmployeesOfProject(Project project) {
        return employeeRepository.findByProjects(project);
    }

    @Override
    public Optional<Employee> findByLastname(String lastname) {
        return employeeRepository.findByLastName(lastname);
    }
}
