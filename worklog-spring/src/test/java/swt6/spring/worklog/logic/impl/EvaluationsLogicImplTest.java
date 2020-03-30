package swt6.spring.worklog.logic.impl;

import org.javatuples.Pair;
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
import swt6.spring.worklog.dto.TimeSpan;
import swt6.spring.worklog.logic.interf.CustomerLogic;
import swt6.spring.worklog.logic.interf.EmployeeLogic;
import swt6.spring.worklog.logic.interf.EvaluationsLogic;
import swt6.spring.worklog.logic.interf.ProjectLogic;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/swt6/applicationContext-jpa2.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Rollback
public class EvaluationsLogicImplTest {
    private EmbeddedDatabase db;

    @Autowired
    private EvaluationsLogic evaluationsLogic;

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
    public void getTimeOfEmplyoeeWorking() {
        Employee emp = employeeLogic.findByLastname("Mayr").get();
        Map<Project, Double> res = evaluationsLogic.getTimeOfEmplyoeeWorkingHoursOnProjects(emp);
        Assert.assertEquals(2, res.size());
        Assert.assertEquals(Double.valueOf(2.5),
                res.entrySet().stream().filter(projectDoubleEntry ->
                        projectDoubleEntry.getKey().getName().equals("Gitlab 2.0")).findFirst().get().getValue());
        Assert.assertEquals(Double.valueOf(4.0),
                res.entrySet().stream().filter(projectDoubleEntry ->
                        projectDoubleEntry.getKey().getName().equals("Youtube 2.0")).findFirst().get().getValue());

    }

    @Test
    public void getCostsOfProject() {
        Employee emp = employeeLogic.findByLastname("Mayr").get();
        Map<TimeSpan, List<Pair<String, Double>>> res = evaluationsLogic.getTimeOfEmplyoeeWorking(emp,
                LocalDateTime.of(2020, 1, 1, 01, 00),
                LocalDateTime.of(2020, 12, 1, 23, 55));
        Assert.assertEquals(3, res.get(TimeSpan.Monate).size());
        Assert.assertEquals(4, res.get(TimeSpan.Tage).size());
        Assert.assertEquals(4, res.get(TimeSpan.Wochen).size());
    }

    @Test
    public void findCustomerByLastname() {

        Project project = projectLogic.findByName("Youtube 2.0").get();
        Pair<Map<CostType, Double>, Map<Employee, Double>> res = evaluationsLogic.getCostsOfProject(project);
        var costs = res.getValue0();
        var emps = res.getValue1();
        Assert.assertEquals(3, costs.size());
        Assert.assertEquals(2, emps.size());
        Assert.assertEquals(Double.valueOf(17.5), costs.entrySet().stream()
                .filter(costTypeDoubleEntry -> costTypeDoubleEntry.getKey() == CostType.Projektleitung)
                .findFirst().get().getValue());
        Assert.assertEquals(Double.valueOf(20.0), costs.entrySet().stream()
                .filter(costTypeDoubleEntry -> costTypeDoubleEntry.getKey() == CostType.Entwicklung)
                .findFirst().get().getValue());
        Assert.assertEquals(Double.valueOf(12.0), costs.entrySet().stream()
                .filter(costTypeDoubleEntry -> costTypeDoubleEntry.getKey() == CostType.Testen)
                .findFirst().get().getValue());
        Assert.assertEquals(Double.valueOf(17.5), emps.entrySet().stream()
                .filter(costTypeDoubleEntry -> costTypeDoubleEntry.getKey().getLastName().equals("Huber"))
                .findFirst().get().getValue());
        Assert.assertEquals(Double.valueOf(32.0), emps.entrySet().stream()
                .filter(costTypeDoubleEntry -> costTypeDoubleEntry.getKey().getLastName().equals("Mayr"))
                .findFirst().get().getValue());

    }
}