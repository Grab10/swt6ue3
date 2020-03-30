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
import swt6.spring.worklog.dto.Invoice;
import swt6.spring.worklog.logic.interf.CustomerLogic;
import swt6.spring.worklog.logic.interf.InvoiceLogic;
import swt6.spring.worklog.logic.interf.ProjectLogic;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/swt6/applicationContext-jpa2.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Rollback
public class InvoiceLogicImplTest {
    private EmbeddedDatabase db;

    @Autowired
    private InvoiceLogic invoiceLogic;
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
    public void get1InvoiceForCustomer() {
        Invoice res = invoiceLogic.getInvoiceOfCustomer(customerLogic.findByLastname("Schmid").get(),
                LocalDateTime.of(2020, 2, 1, 10, 15),
                LocalDateTime.of(2020, 2, 1, 13, 15));
        Assert.assertEquals(1, res.getLogbookEntries().size());
        Assert.assertEquals(Double.valueOf(17.5), res.getCosts());
    }

    @Test
    public void getInvoiceForCustomerOf2020() {
        var res = invoiceLogic.getInvoiceOfCustomer(customerLogic.findByLastname("Schmid").get(),
                LocalDateTime.of(2020, 1, 1, 00, 00),
                LocalDateTime.of(2020, 12, 31, 23, 55));
        Assert.assertEquals(4, res.getLogbookEntries().size());
        Assert.assertEquals(Double.valueOf(49.5), res.getCosts());
    }

    @Test
    public void getInvoiceForProject() {
        var res = invoiceLogic.getInvoiceOfProject(projectLogic.findByName("Gitlab 2.0").get(),
                LocalDateTime.of(2020, 1, 1, 00, 00),
                LocalDateTime.of(2020, 12, 31, 23, 55));
        Assert.assertEquals(5, res.getLogbookEntries().size());
        Assert.assertEquals(Double.valueOf(69), res.getCosts());

    }
}