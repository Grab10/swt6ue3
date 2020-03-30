package swt6.spring.worklog.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project implements BaseDomain {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Employee> members = new HashSet<>();

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Fetch(FetchMode.SELECT)
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer = null;

    public Customer getCustomer() {
        return customer;
    }

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "project", cascade = {CascadeType.MERGE},
            fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<LogbookEntry> entries = new HashSet<>();

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getMembers() {
        return members;
    }

    public void setMembers(Set<Employee> members) {
        this.members = members;
    }


    @Override
    public void detach() {
        for (Employee member : new ArrayList<>(members)) {
            member.getProjects().remove(this);
            members.remove(member);
        }
        customer.getProjects().remove(this);
    }


    public Set<LogbookEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<LogbookEntry> entries) {
        this.entries = entries;
    }
}
