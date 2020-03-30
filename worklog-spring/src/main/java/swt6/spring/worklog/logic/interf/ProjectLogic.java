package swt6.spring.worklog.logic.interf;

import swt6.spring.worklog.domain.Customer;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProjectLogic {
    Project syncProject(Project project);

    void AddEmployeeToProject(Project project, Employee employee);

    void RemoveEmployeeToProject(Project project, Employee employee);

    List<Project> findAllProjects();

    List<Project> findAllProjectsOfCustomer(Customer customer);

    Optional<Project> findByName(String name);
}
