package swt6.spring.worklog.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.dao.LogbookEntryRepository;
import swt6.spring.worklog.dao.ProjectRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.logic.interf.EmployeeLogic;
import swt6.spring.worklog.logic.interf.LogbookEntryLogic;

import java.util.List;
import java.util.Optional;

@Service("logbookEntryLogic")
@Primary
@Transactional
public class LogbookEntryLogicImpl implements LogbookEntryLogic {
    @Autowired
    private LogbookEntryRepository logbookEntryRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public LogbookEntry syncLogbookEntry(LogbookEntry entry) {
        return logbookEntryRepository.saveAndFlush(entry);
    }

    @Override
    @Transactional(readOnly = true)
    public LogbookEntry findLogbookEntryById(Long id) {
        return logbookEntryRepository.findById(id).orElse(null);
    }


    @Override
    public void addEmployeeToLogbookEntry(LogbookEntry logbookEntry, Employee employee) {
        logbookEntry = logbookEntryRepository.saveAndFlush(logbookEntry);
        employee = employeeRepository.saveAndFlush(employee);

        employee.getLogbookEntries().add(logbookEntry);
        if (logbookEntry.getEmployee() != null) {
            logbookEntry.getEmployee().getLogbookEntries().remove(logbookEntry);
        }

        logbookEntry.setEmployee(employee);
        logbookEntry = logbookEntryRepository.saveAndFlush(logbookEntry);
        employee = employeeRepository.saveAndFlush(employee);
    }

    @Override
    public void removeEmployeeToLogbookEntry(LogbookEntry logbookEntry, Employee employee) {
        logbookEntry = logbookEntryRepository.saveAndFlush(logbookEntry);
        employee = employeeRepository.saveAndFlush(employee);

        employee.getLogbookEntries().remove(logbookEntry);
        logbookEntry.setEmployee(null);

        logbookEntry = logbookEntryRepository.saveAndFlush(logbookEntry);
        employee = employeeRepository.saveAndFlush(employee);
    }

    @Override
    public void addProjectToLogbookEntry(LogbookEntry logbookEntry, Project project) {
        logbookEntry = logbookEntryRepository.saveAndFlush(logbookEntry);
        project = projectRepository.saveAndFlush(project);

        project.getEntries().add(logbookEntry);
        if (logbookEntry.getProject() != null) {
            logbookEntry.getProject().getEntries().remove(logbookEntry);
        }

        logbookEntry.setProject(project);
        logbookEntry = logbookEntryRepository.saveAndFlush(logbookEntry);
        project = projectRepository.saveAndFlush(project);
    }

    @Override
    public void removeProjectToLogbookEntry(LogbookEntry logbookEntry, Project project) {
        logbookEntry = logbookEntryRepository.saveAndFlush(logbookEntry);
        project = projectRepository.saveAndFlush(project);

        project.getEntries().remove(logbookEntry);
        logbookEntry.setProject(null);

        logbookEntry = logbookEntryRepository.saveAndFlush(logbookEntry);
        project = projectRepository.saveAndFlush(project);
    }

    @Override
    public Optional<LogbookEntry> findByActivity(String activity) {
        return logbookEntryRepository.findByActivity(activity);
    }

    @Override
    public List<LogbookEntry> findAll() {
        return logbookEntryRepository.findAll();
    }
}
