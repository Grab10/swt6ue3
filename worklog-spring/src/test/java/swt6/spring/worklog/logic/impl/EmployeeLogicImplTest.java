package swt6.spring.worklog.logic.impl;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
public class EmployeeLogicImplTest {
    private EmbeddedDatabase db;

    @Autowired
    private EmployeeLogic employeeLogic;

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
    public void syncEmployee() {
        employeeLogic.syncEmployee(new Employee("Hans", "Walther",
                LocalDate.of(1980, 12, 8)));
        Employee employee = employeeLogic.findByLastname("Walther").get();
        Assert.assertNotNull(employee);
    }

    @Test
    public void UpdateEmployee() {
        Employee employee = employeeLogic.findByLastname("Mayr").get();
        employee.setLastName("Meier");
        employeeLogic.syncEmployee(employee);
        employee = employeeLogic.findByLastname("Meier").get();
        Assert.assertNotNull(employee);
    }

    @Test
    public void findAllEmployees() {
        assertEquals(4, employeeLogic.findAllEmployees().size());
    }

    @Test
    public void findAllEmployeesOfProject() {
        var proj = projectLogic.findByName("Youtube 2.0").get();
        var emps = employeeLogic.findAllEmployeesOfProject(proj);
        assertEquals(2, emps.size());
    }

    @Test
    public void findByLastname() {
        Employee employee = employeeLogic.findByLastname("Mayr").get();
        Assert.assertNotNull(employee);
    }
}