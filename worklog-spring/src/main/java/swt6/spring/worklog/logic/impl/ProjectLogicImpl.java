package swt6.spring.worklog.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.dao.ProjectRepository;
import swt6.spring.worklog.domain.Customer;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.logic.interf.ProjectLogic;

import java.util.List;
import java.util.Optional;

@Service("projectLogic")
@Primary
@Transactional
public class ProjectLogicImpl implements ProjectLogic {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Project syncProject(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    @Override
    public void AddEmployeeToProject(Project project, Employee employee) {
        project = projectRepository.saveAndFlush(project);
        employee = employeeRepository.saveAndFlush(employee);
        employee.getProjects().add(project);
        project.getMembers().add(employee);
        project = projectRepository.saveAndFlush(project);
        employee = employeeRepository.saveAndFlush(employee);
    }

    @Override
    public void RemoveEmployeeToProject(Project project, Employee employee) {
        project = projectRepository.saveAndFlush(project);
        employee = employeeRepository.saveAndFlush(employee);
        employee.getProjects().remove(project);
        project.getMembers().remove(employee);
        project = projectRepository.saveAndFlush(project);
        employee = employeeRepository.saveAndFlush(employee);
        projectRepository.saveAndFlush(project);
    }


    @Override
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findAllProjectsOfCustomer(Customer customer) {
        return projectRepository.findAllByCustomer(customer);
    }


    @Override
    public Optional<Project> findByName(String name) {
        return projectRepository.findByName(name);
    }
}
