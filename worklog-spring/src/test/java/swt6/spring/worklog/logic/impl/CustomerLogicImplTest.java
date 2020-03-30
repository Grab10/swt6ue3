package swt6.spring.worklog.logic.impl;

import org.junit.Assert;

import org.junit.Test;
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
import swt6.spring.worklog.logic.interf.CustomerLogic;
import swt6.spring.worklog.logic.interf.ProjectLogic;

import javax.transaction.Transactional;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/swt6/applicationContext-jpa2.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Rollback
public class CustomerLogicImplTest {
    private EmbeddedDatabase db;

    @Autowired
    private CustomerLogic customerLogic;

    @Autowired
    private ProjectLogic projectLogic;

    @BeforeAll
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.DERBY)
                .addScript("create.sql")
                .addScript("insert.sql")
                .build();
    }


    @Test
    public void addCustomerToProject() {
        Project project = projectLogic.findByName("Youtube 2.0").get();
        Customer customer = customerLogic.findByLastname("Graf").get();
        customerLogic.AddCustomerToProject(project, customer);
        project = projectLogic.findByName("Youtube 2.0").get();
        customer = customerLogic.findByLastname("Graf").get();
        Project finalProject = project;
        Assert.assertNotNull(customer.getProjects().stream().filter(project1 -> project1.getName().equals(finalProject.getName())).findFirst().orElse(null));
        Assert.assertEquals(project.getCustomer().getLastName(), customer.getLastName());
    }

    @Test
    public void removeCustomerFromProject() {
        Project project = projectLogic.findByName("Gitlab 2.0").get();
        Customer customer = customerLogic.findByLastname("Scholz").get();
        customerLogic.RemoveCustomerFromProject(project, customer);
        project = projectLogic.findByName("Gitlab 2.0").get();
        customer = customerLogic.findByLastname("Scholz").get();
        Project finalProject = project;
        Assert.assertNull(customer.getProjects().stream().filter(project1 -> project1.getName().equals(finalProject.getName())).findFirst().orElse(null));
        Assert.assertNull(project.getCustomer());
    }

    @Test
    public void syncCustomer() {
        customerLogic.syncCustomer(new Customer("Johann", "Walther",
                LocalDate.of(1980, 12, 8),
                new Address("4062", "Kirchberg", "Hörschingerstraße 12")));
        Customer customer = customerLogic.findByLastname("Walther").get();
        Assert.assertNotNull(customer);
    }

    @Test
    public void UpdateCustomer() {
        Customer customer = customerLogic.findByLastname("Schmid").get();
        customer.setLastName("Meier");
        customerLogic.syncCustomer(customer);
        customer = customerLogic.findByLastname("Meier").get();
        Assert.assertNotNull(customer);
    }

    @Test
    public void findAllCustomers() {
        assertEquals(3, customerLogic.findAllCustomers().size());
    }

    @Test
    public void findCustomerByLastname() {
        Customer customer = customerLogic.findByLastname("Scholz").get();
        System.out.println(customerLogic.findAllCustomers());
        Assert.assertNotNull(customer);
    }
}