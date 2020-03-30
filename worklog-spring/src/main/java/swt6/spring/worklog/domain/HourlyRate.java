package swt6.spring.worklog.domain;

import javax.persistence.*;

@Entity
public class HourlyRate implements BaseDomain {
    @Id
    @GeneratedValue
    private Long id;
    private double rate;
    @Enumerated(EnumType.STRING)
    private EmployeeCategory employeeCategory;
    @Column(name = "ryear")
    private int year;

    @Override
    public String toString() {
        return "HourlyRate{" +
                "id=" + id +
                ", rate=" + rate +
                ", employeeCategory=" + employeeCategory +
                ", year=" + year +
                '}';
    }

    public HourlyRate() {
    }

    public HourlyRate(double rate, EmployeeCategory employeeCategory, int year) {
        this.employeeCategory = employeeCategory;
        this.rate = rate;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public EmployeeCategory getEmployeeCategory() {
        return employeeCategory;
    }

    public void setEmployeeCategory(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public void detach() {

    }
}
