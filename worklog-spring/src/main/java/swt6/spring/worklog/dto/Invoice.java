package swt6.spring.worklog.dto;


import org.javatuples.Triplet;
import swt6.spring.worklog.domain.*;


import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Invoice {
    private List<LogbookEntry> logbookEntries;

    Map<Project, Map<CostType, Map<EmployeeCategory, List<Triplet<Employee, Double, Double>>>>> positions = new HashMap<>();
    private Double costs;
    private Double hours;

    public void setLogbookEntries(List<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public List<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public Invoice(List<LogbookEntry> logbookEntries) {
        costs = 0.0;
        hours = 0.0;
        this.logbookEntries = logbookEntries;
        if (!logbookEntries.isEmpty()) {

            this.logbookEntries.forEach(logbookEntry ->
            {

                double minutes = (Duration.between(logbookEntry.getStartTime(), logbookEntry.getEndTime())).toMinutes();
                double cost = (minutes / 60) * logbookEntry.getEmployee().getHourlyRate().getRate();

                if (!positions.containsKey(logbookEntry.getProject())) {
                    positions.put(logbookEntry.getProject(), new HashMap<>());
                }
                if (!positions.get(logbookEntry.getProject()).containsKey(logbookEntry.getCostType())) {
                    positions.get(logbookEntry.getProject()).put(logbookEntry.getCostType(), new HashMap<>());
                }
                if (!positions.get(logbookEntry.getProject()).get(logbookEntry.getCostType()).containsKey(logbookEntry.getEmployee().getHourlyRate().getEmployeeCategory())) {
                    positions.get(logbookEntry.getProject()).get(logbookEntry.getCostType()).put(logbookEntry.getEmployee().getHourlyRate().getEmployeeCategory(), new ArrayList<>());
                }
                positions.get(logbookEntry.getProject()).get(logbookEntry.getCostType()).get(logbookEntry.getEmployee().getHourlyRate().getEmployeeCategory()).add(
                        Triplet.with(logbookEntry.getEmployee(), (Double) minutes, (Double) cost)
                );
                costs += cost;
                hours += minutes / 60;
            });
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        positions.forEach((project, map) ->
        {
            builder.append("Projektnamen: " + project.getName() + "\n");
            map.forEach((costType, employeeCategoryTripletMap) ->
            {
                builder.append("\t" + costType + "\n");
                employeeCategoryTripletMap.forEach((employeeCategory, tripletList) -> {
                    builder.append("\t" + "\t" + employeeCategory + "\n");
                    tripletList.forEach(triplet -> {
                        builder.append("\t" + "\t" + "\t" + triplet.getValue0().getLastName() + " / Dauer in Minuten:  " + triplet.getValue1() + " / " + triplet.getValue2() + "€ \n");
                    });
                });
            });
            builder.append("Studen: " + hours + "\n");
            builder.append("Kosten: " + costs + "€\n" + "\n");
        });

        return builder.toString();
    }

    public Map<Project, Map<CostType, Map<EmployeeCategory, List<Triplet<Employee, Double, Double>>>>> getPositions() {
        return positions;
    }

    public void setPositions(Map<Project, Map<CostType, Map<EmployeeCategory, List<Triplet<Employee, Double, Double>>>>> positions) {
        this.positions = positions;
    }

    public Double getCosts() {
        return costs;
    }

    public void setCosts(Double costs) {
        this.costs = costs;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }
}


