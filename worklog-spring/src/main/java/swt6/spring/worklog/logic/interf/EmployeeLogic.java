package swt6.spring.worklog.logic.interf;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeLogic {
    Employee syncEmployee(Employee employee);

    @Transactional(readOnly = true)
    Employee findEmployeeById(Long id);

    @Transactional(readOnly = true)
    List<Employee> findAllEmployees();

    List<Employee> findAllEmployeesOfProject(Project project);

    Optional<Employee> findByLastname(String lastname);
}
