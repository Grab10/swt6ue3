package swt6.spring.worklog.logic.interf;

import org.javatuples.Pair;
import swt6.spring.worklog.domain.CostType;
import swt6.spring.worklog.domain.Customer;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.dto.TimeSpan;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EvaluationsLogic {
    Map<TimeSpan, List<Pair<String, Double>>> getTimeOfEmplyoeeWorking(Employee employee, LocalDateTime from, LocalDateTime to);

    Pair<Map<CostType, Double>, Map<Employee, Double>> getCostsOfProject(Project project);

    Map<Project, Double> getTimeOfEmplyoeeWorkingHoursOnProjects(Employee emp);
}
