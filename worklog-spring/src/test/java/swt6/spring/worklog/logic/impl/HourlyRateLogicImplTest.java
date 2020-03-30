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
import swt6.spring.worklog.logic.interf.HourlyRateLogic;
import swt6.spring.worklog.logic.interf.ProjectLogic;

import javax.transaction.Transactional;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/swt6/applicationContext-jpa2.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Rollback
public class HourlyRateLogicImplTest {
    private EmbeddedDatabase db;

    @Autowired
    private HourlyRateLogic hourlyRateLogic;

    @BeforeEach
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.DERBY)
                .addScript("create.sql")
                .addScript("insert.sql")
                .build();
    }

    @Test
    public void findAllHourlyRates() {
        Assert.assertEquals(3, hourlyRateLogic.findAllHourlyRates().size());
    }

    @Test
    public void insertHourlyRate() {
        var rate = new HourlyRate(5, EmployeeCategory.SeniorDeveloper, 2000);
        rate = hourlyRateLogic.syncHourlyRate(rate);
        Assert.assertTrue(hourlyRateLogic.findAllHourlyRates().contains(rate));
    }


}