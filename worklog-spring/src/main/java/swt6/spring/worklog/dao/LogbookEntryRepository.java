package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Project;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LogbookEntryRepository extends JpaRepository<LogbookEntry, Long> {
    List<LogbookEntry> findAllByEmployee(Employee employee);

    List<LogbookEntry> findAllByProject(Project project);

    List<LogbookEntry> findAllByEmployeeAndStartTimeAfterAndEndTimeBefore(Employee employee, LocalDateTime startTime, LocalDateTime endTime);

    Optional<LogbookEntry> findByActivity(String activity);
}
