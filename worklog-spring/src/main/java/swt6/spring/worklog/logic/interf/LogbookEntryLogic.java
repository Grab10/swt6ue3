package swt6.spring.worklog.logic.interf;

import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Project;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface LogbookEntryLogic {
    LogbookEntry syncLogbookEntry(LogbookEntry entry);

    @Transactional(readOnly = true)
    LogbookEntry findLogbookEntryById(Long id);

    void addEmployeeToLogbookEntry(LogbookEntry logbookEntry, Employee employee);

    void removeEmployeeToLogbookEntry(LogbookEntry logbookEntry, Employee employee);

    void addProjectToLogbookEntry(LogbookEntry logbookEntry, Project project);

    void removeProjectToLogbookEntry(LogbookEntry logbookEntry, Project project);

    Optional<LogbookEntry> findByActivity(String activity);

    List<LogbookEntry> findAll();
}
