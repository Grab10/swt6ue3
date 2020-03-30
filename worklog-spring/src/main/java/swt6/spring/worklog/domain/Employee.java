package swt6.spring.worklog.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "employee_type",
//                        discriminatorType=DiscriminatorType.STRING)
public class Employee implements BaseDomain {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "employee", cascade = {CascadeType.MERGE},
            fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private HourlyRate hourlyRate;

    @ManyToMany(mappedBy = "members", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();

    // MANDATORY!!!
    public Employee() {
    }

    public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public HourlyRate getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(HourlyRate hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void addLogbookEntry(LogbookEntry lbe) {
        if (lbe.getEmployee() != null) {
            lbe.getEmployee().getLogbookEntries().remove(lbe);
        }

        this.logbookEntries.add(lbe);
        lbe.setEmployee(this);
    }

    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        project.getMembers().add(this);
        this.projects.add(project);
    }

    public void detach() {
        for (LogbookEntry entry : new ArrayList<>(this.logbookEntries)) {
            entry.setEmployee(null);
            this.logbookEntries.remove(entry);
        }

        for (Project project : new ArrayList<>(this.projects)) {
            project.getMembers().remove(this);
            this.projects.remove(project);
        }
    }

}
