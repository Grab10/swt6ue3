package swt6.spring.worklog.logic.impl;

import org.junit.Assert;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import swt6.spring.worklog.domain.*;
import swt6.spring.worklog.logic.interf.CustomerLogic;
import swt6.spring.worklog.logic.interf.EmployeeLogic;
import swt6.spring.worklog.logic.interf.ProjectLogic;

import javax.transaction.Transactional;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/swt6/applicationContext-jpa2.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Rollback
public class ProjectLogicImplTest {
    private EmbeddedDatabase db;

    @Autowired
    private EmployeeLogic employeeLogic;

    @Autowired
    private CustomerLogic customerLogic;

    @Autowired
    private ProjectLogic projectLogic;

    @BeforeEach
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.DERBY)
                .addScript("create.sql")
                .addScript("insert.sql")
                .build();
    }

    @Test
    public void syncProject() {
        var proj = projectLogic.findByName("Youtube 2.0").get();
        proj.setName("Youtube 3.0");
        proj = projectLogic.syncProject(proj);
        proj = projectLogic.findByName("Youtube 3.0").get();
        Assert.assertNotNull(proj);
    }

    @Test
    public void InsertProject() {
        var proj = projectLogic.syncProject(new Project("Amazon 2.0"));
        projectLogic.syncProject(proj);
        proj = projectLogic.findByName("Amazon 2.0").get();
        Assert.assertNotNull(proj);
    }

    @Test
    public void addEmployeeToProject() {
        Project project = projectLogic.findByName("Youtube 2.0").get();
        Employee employee = employeeLogic.findByLastname("Huemer").get();
        projectLogic.AddEmployeeToProject(project, employee);
        project = projectLogic.findByName("Youtube 2.0").get();
        employee = employeeLogic.findByLastname("Huemer").get();
        Project finalProject = project;
        Assert.assertNotNull(employee.getProjects().stream().filter(project1 -> project1.getName().equals(finalProject.getName())).findFirst().orElse(null));
        Employee finalEmployee = employee;
        Assert.assertNotNull(project.getMembers().stream().filter(emps -> emps.getLastName().equals(finalEmployee.getLastName())).findFirst().orElse(null));
    }

    @Test
    public void removeEmployeeToProject() {
        Project project = projectLogic.findByName("Youtube 2.0").get();
        Employee employee = employeeLogic.findByLastname("Huber").get();
        projectLogic.RemoveEmployeeToProject(project, employee);
        project = projectLogic.findByName("Youtube 2.0").get();
        employee = employeeLogic.findByLastname("Huber").get();
        Project finalProject = project;
        Assert.assertNull(employee.getProjects().stream().filter(project1 -> project1.getName().equals(finalProject.getName())).findFirst().orElse(null));
        Employee finalEmployee = employee;
        Assert.assertNull(project.getMembers().stream().filter(emps -> emps.getLastName().equals(finalEmployee.getLastName())).findFirst().orElse(null));
    }

    @Test
    public void findAllProjects() {
        assertEquals(3, projectLogic.findAllProjects().size());
    }

    @Test
    public void findAllProjectsOfCustomer() {
        Customer customer = customerLogic.findByLastname("Schmid").get();
        assertEquals(2, projectLogic.findAllProjectsOfCustomer(customer).size());
    }

    @Test
    public void FindByName() {
        var proj = projectLogic.findByName("Youtube 2.0").get();
        Assert.assertNotNull(proj);
    }

}