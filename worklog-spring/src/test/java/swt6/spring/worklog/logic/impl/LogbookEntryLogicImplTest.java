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
import swt6.spring.worklog.logic.interf.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/swt6/applicationContext-jpa2.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Rollback
public class LogbookEntryLogicImplTest {
    private EmbeddedDatabase db;

    @Autowired
    private LogbookEntryLogic logbookEntryLogic;

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
    public void syncLogbookEntry() {
        LogbookEntry entry = logbookEntryLogic.findByActivity("Kaffee trinken").get();
        entry.setActivity("Mehr Kaffee trinken");
        logbookEntryLogic.syncLogbookEntry(entry);
        entry = logbookEntryLogic.findByActivity("Mehr Kaffee trinken").get();
        Assert.assertNotNull(entry);
    }

    @Test
    public void insertLogbookEntry() {
        LogbookEntry entry = new LogbookEntry("Super Wartung", LocalDateTime.now().minusMinutes(15), LocalDateTime.now(), CostType.Entwicklung);
        logbookEntryLogic.syncLogbookEntry(entry);
        entry = logbookEntryLogic.findByActivity("Super Wartung").get();
        Assert.assertNotNull(entry);
    }

    @Test
    public void findLogbookEntryById() {
        var log = logbookEntryLogic.findLogbookEntryById(32L);
        Assert.assertEquals("Kaffee trinken", log.getActivity());
    }


    @Test
    public void addEmployeeToLogbookEntry() {
        Employee employee = employeeLogic.findByLastname("Mayr").get();
        LogbookEntry entry = new LogbookEntry("Super Wartung", LocalDateTime.now().minusMinutes(15), LocalDateTime.now(), CostType.Entwicklung);
        logbookEntryLogic.syncLogbookEntry(entry);

        logbookEntryLogic.addEmployeeToLogbookEntry(entry, employee);

        employee = employeeLogic.findByLastname("Mayr").get();
        entry = logbookEntryLogic.findByActivity("Super Wartung").get();

        LogbookEntry finalEntry = entry;

        Assert.assertNotNull(employee.getLogbookEntries().stream().filter(log -> log.getActivity().equals(finalEntry.getActivity())).findFirst().orElse(null));
        Assert.assertEquals(entry.getEmployee().getLastName(), employee.getLastName());
    }

    @Test
    public void removeEmployeeToLogbookEntry() {
        Employee employee = employeeLogic.findByLastname("Mayr").get();
        LogbookEntry entry = logbookEntryLogic.findByActivity("Kaffee trinken").get();

        logbookEntryLogic.removeEmployeeToLogbookEntry(entry, employee);

        employee = employeeLogic.findByLastname("Mayr").get();
        entry = logbookEntryLogic.findByActivity("Kaffee trinken").get();

        LogbookEntry finalEntry = entry;

        Assert.assertNull(employee.getLogbookEntries().stream().filter(log -> log.getActivity().equals(finalEntry.getActivity())).findFirst().orElse(null));
        Assert.assertNull(entry.getEmployee());
    }

    @Test
    public void addProjectToLogbookEntry() {
        Project project = projectLogic.findByName("Youtube 2.0").get();
        LogbookEntry entry = new LogbookEntry("Super Wartung", LocalDateTime.now().minusMinutes(15), LocalDateTime.now(), CostType.Entwicklung);
        logbookEntryLogic.syncLogbookEntry(entry);

        logbookEntryLogic.addProjectToLogbookEntry(entry, project);

        project = projectLogic.findByName("Youtube 2.0").get();
        entry = logbookEntryLogic.findByActivity("Super Wartung").get();

        Assert.assertEquals(entry.getProject().getName(), project.getName());
        LogbookEntry finalEntry = entry;
        Assert.assertNotNull(project.getEntries().stream().filter(log -> log.getActivity().equals(finalEntry.getActivity())).findFirst().orElse(null));
    }

    @Test
    public void removeProjectToLogbookEntry() {
        Project project = projectLogic.findByName("Youtube 2.0").get();
        LogbookEntry entry = logbookEntryLogic.findByActivity("Kaffee trinken").get();
        logbookEntryLogic.syncLogbookEntry(entry);

        logbookEntryLogic.removeProjectToLogbookEntry(entry, project);

        project = projectLogic.findByName("Youtube 2.0").get();
        entry = logbookEntryLogic.findByActivity("Kaffee trinken").get();

        Assert.assertNull(entry.getProject());
        LogbookEntry finalEntry = entry;
        Assert.assertNull(project.getEntries().stream().filter(log -> log.getActivity().equals(finalEntry.getActivity())).findFirst().orElse(null));
    }
}