package swt6.spring.worklog.logic.impl;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.LogbookEntryRepository;
import swt6.spring.worklog.domain.*;
import swt6.spring.worklog.dto.TimeSpan;
import swt6.spring.worklog.logic.interf.EvaluationsLogic;

import javax.persistence.TypedQuery;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

@Service("evaluationsLogic")
@Primary
@Transactional
public class EvaluationsLogicImpl implements EvaluationsLogic {
    @Autowired
    private LogbookEntryRepository logbookEntryRepository;

    @Override
    public Map<TimeSpan, List<Pair<String, Double>>> getTimeOfEmplyoeeWorking(Employee employee, LocalDateTime from, LocalDateTime to) {
        Map<TimeSpan, List<Pair<String, Double>>> workingTime = new HashMap<>();

        List<LogbookEntry> results = logbookEntryRepository.findAllByEmployeeAndStartTimeAfterAndEndTimeBefore(employee, from, to);

        TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfYear();
        Map<Month, List<LogbookEntry>> monthly = results.stream().collect(groupingBy(logbookEntry -> logbookEntry.getStartTime().getMonth()));
        Map<Integer, List<LogbookEntry>> weekly = results.stream().collect(Collectors.groupingBy(
                d -> d.getStartTime().get(weekOfYear),
                LinkedHashMap::new,
                Collectors.toList()
        ));

        Map<DayOfWeek, List<LogbookEntry>> daily = results.stream().collect(groupingBy(logbookEntry -> logbookEntry.getStartTime().getDayOfWeek()));

        workingTime.put(TimeSpan.Tage, new ArrayList<>());
        workingTime.put(TimeSpan.Wochen, new ArrayList<>());
        workingTime.put(TimeSpan.Monate, new ArrayList<>());

        daily.forEach((day, logbookEntries) -> {
            Double min = getMinutes(logbookEntries);
            workingTime.get(TimeSpan.Tage).add(new Pair<>(day.name(), min / 60));
        });

        weekly.forEach((weekOfTheYear, logbookEntries) -> {
            Double min = getMinutes(logbookEntries);
            workingTime.get(TimeSpan.Wochen).add(new Pair<>("Woche " + weekOfTheYear, min / 60));
        });

        monthly.forEach((month, logbookEntries) -> {
            Double min = getMinutes(logbookEntries);
            workingTime.get(TimeSpan.Monate).add(new Pair<>(month.name(), min / 60));
        });


        return workingTime;
    }

    @Override
    public Pair<Map<CostType, Double>, Map<Employee, Double>> getCostsOfProject(Project project) {
        Pair<Map<CostType, Double>, Map<Employee, Double>> costs = new Pair<>(new HashMap<>(), new HashMap<>());
        List<LogbookEntry> results = logbookEntryRepository.findAllByProject(project);
        results.forEach(logbookEntry -> {
            double minutes = Duration.between(logbookEntry.getStartTime(), logbookEntry.getEndTime()).toMinutes();
            double cost = logbookEntry.getEmployee().getHourlyRate().getRate() * (minutes / 60.0);
            if (!costs.getValue0().containsKey(logbookEntry.getCostType())) {
                costs.getValue0().put(logbookEntry.getCostType(), cost);
            } else {
                costs.getValue0().put(logbookEntry.getCostType(), costs.getValue0().get(logbookEntry.getCostType()) + cost);
            }
            if (!costs.getValue1().containsKey(logbookEntry.getEmployee())) {
                costs.getValue1().put(logbookEntry.getEmployee(), cost);
            } else {
                costs.getValue1().put(logbookEntry.getEmployee(), costs.getValue1().get(logbookEntry.getEmployee()) + cost);
            }
        });
        return costs;
    }

    @Override
    public Map<Project, Double> getTimeOfEmplyoeeWorkingHoursOnProjects(Employee emp) {
        Map<Project, Double> projects = new HashMap<>();

        var results = logbookEntryRepository.findAllByEmployee(emp);

        results.stream().collect(groupingBy(LogbookEntry::getProject)).forEach((project, logbookEntries) ->
        {
            double time = logbookEntries.stream().collect(summingDouble(logbookEntry -> (Duration.between(logbookEntry.getStartTime(), logbookEntry.getEndTime())).toMinutes()));
            time = time / 60;
            projects.put(project, time);
        });

        return projects;
    }


    private Double getMinutes(List<LogbookEntry> logbookEntries) {
        return logbookEntries.stream().collect(summingDouble(logbookEntry -> (Duration.between(logbookEntry.getStartTime(), logbookEntry.getEndTime())).toMinutes()));
    }
}
