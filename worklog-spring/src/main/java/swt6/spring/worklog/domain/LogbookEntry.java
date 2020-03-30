package swt6.spring.worklog.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LogbookEntry {

    @Id
    @GeneratedValue
    private Long id;
    private String activity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private CostType costType;

    @Override
    public String toString() {
        return "LogbookEntry{" +
                "id=" + id +
                ", activity='" + activity + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", costType=" + costType +
                ", project=" + project +
                ", employee=" + employee +
                '}';
    }

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public CostType getCostType() {
        return costType;
    }

    public void setCostType(CostType costType) {
        this.costType = costType;
    }


    public LogbookEntry() {
    }

    public LogbookEntry(String activity, LocalDateTime startTime, LocalDateTime endTime) {
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;

    }

    public LogbookEntry(String activity, LocalDateTime startTime, LocalDateTime endTime, CostType costType) {
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.costType = costType;
    }

    public LogbookEntry(String activity, LocalDateTime startTime, LocalDateTime endTime, Project project, Employee employee, CostType costType) {
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.project = project;
        this.employee = employee;
        this.costType = costType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void attachEmployee(Employee empl) {
        if (this.employee != null) {
            this.employee.getLogbookEntries().remove(this);
        }
        if (empl != null) {
            empl.getLogbookEntries().add(this);
        }
        this.employee = empl;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    public void detachEmployee() {
        if (this.employee != null) {
            this.employee.getLogbookEntries().remove(this);
        }
        this.employee = null;
    }
}
