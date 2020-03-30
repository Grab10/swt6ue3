package swt6.spring.worklog.console;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import swt6.spring.worklog.domain.*;
import swt6.spring.worklog.dto.Invoice;
import swt6.spring.worklog.logic.interf.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class CommandLine {
    static String promptFor(BufferedReader in, String p) {
        System.out.print(p + "> ");
        System.out.flush();
        try {
            return in.readLine();
        } catch (Exception e) {
            return promptFor(in, p);
        }
    }

    public void start() {
        try (AbstractApplicationContext factory =
                     new ClassPathXmlApplicationContext(
                             "swt6/spring/worklog/test/applicationContext-jpa2.xml")) {
            final CustomerLogic customerLogic =
                    factory.getBean("customerLogic", CustomerLogic.class);
            final EmployeeLogic employeeLogic =
                    factory.getBean("employeeLogic", EmployeeLogic.class);
            final HourlyRateLogic hourlyRateLogic =
                    factory.getBean("hourlyRateLogic", HourlyRateLogic.class);
            final EvaluationsLogic evaluationsLogic =
                    factory.getBean("evaluationsLogic", EvaluationsLogic.class);
            final LogbookEntryLogic logbookEntryLogic =
                    factory.getBean("logbookEntryLogic", LogbookEntryLogic.class);
            final ProjectLogic projectLogic =
                    factory.getBean("projectLogic", ProjectLogic.class);
            final InvoiceLogic invoiceLogic =
                    factory.getBean("invoiceLogic", InvoiceLogic.class);
            var empl = new Employee("Hans", "Huber",
                    LocalDate.of(1980, 12, 8));
            empl.setAddress(new Address("4064", "Oftering", "Landstraße 1"));
            var rate1 = new HourlyRate(10, EmployeeCategory.Projektleiter, 2020);
            rate1 = hourlyRateLogic.syncHourlyRate(rate1);
            empl.setHourlyRate(rate1);

            var empl1 = new Employee("Franz", "Mayr",
                    LocalDate.of(1990, 1, 5));
            empl1.setAddress(new Address("4062", "Kirchberg", "Hauptstraße 1"));
            var rate2 = new HourlyRate(8, EmployeeCategory.SeniorDeveloper, 2020);
            rate2 = hourlyRateLogic.syncHourlyRate(rate2);
            empl1.setHourlyRate(rate2);

            var rate = new HourlyRate(7, EmployeeCategory.JuniorDeveloper, 2020);
            rate = hourlyRateLogic.syncHourlyRate(rate);

            var empl2 = new Employee("Karl", "Huemer",
                    LocalDate.of(1987, 3, 21));
            empl2.setAddress(new Address("4062", "Kirchberg", "Sonnenblumenstraße 2"));
            empl2.setHourlyRate(rate);

            var empl3 = new Employee("Max", "Horn",
                    LocalDate.of(1970, 1, 21));
            empl3.setAddress(new Address("4072", "Alkoven", "Falkenweg 55"));
            empl3.setHourlyRate(rate);


            employeeLogic.syncEmployee(empl);
            employeeLogic.syncEmployee(empl1);
            employeeLogic.syncEmployee(empl2);
            employeeLogic.syncEmployee(empl3);

            var cust = new Customer("Markus", "Schmid",
                    LocalDate.of(1950, 8, 15),
                    new Address("4072", "Alkoven", "Gartenstraße 12"));
            var cust1 = new Customer("Kaiser", "Scholz",
                    LocalDate.of(1960, 2, 5),
                    new Address("4072", "Alkoven", "Falkenweg 55"));
            var cust2 = new Customer("Gerlach", "Graf",
                    LocalDate.of(1960, 5, 2),
                    new Address("4070", "Eferding", "Erlenweg 4"));


            cust = customerLogic.syncCustomer(cust);
            cust1 = customerLogic.syncCustomer(cust1);
            cust2 = customerLogic.syncCustomer(cust2);


            var proj = new Project("Youtube 2.0");
            projectLogic.AddEmployeeToProject(proj, empl);
            projectLogic.AddEmployeeToProject(proj, empl1);
            customerLogic.AddCustomerToProject(proj, cust);

            var proj1 = new Project("Google 2.0");
            projectLogic.AddEmployeeToProject(proj1, empl);
            projectLogic.AddEmployeeToProject(proj1, empl2);
            customerLogic.AddCustomerToProject(proj1, cust);

            var proj2 = new Project("Gitlab 2.0");
            projectLogic.AddEmployeeToProject(proj2, empl);
            projectLogic.AddEmployeeToProject(proj2, empl1);
            projectLogic.AddEmployeeToProject(proj2, empl2);
            projectLogic.AddEmployeeToProject(proj2, empl3);
            customerLogic.AddCustomerToProject(proj2, cust1);

            proj = projectLogic.syncProject(proj);
            proj1 = projectLogic.syncProject(proj1);
            proj2 = projectLogic.syncProject(proj2);

            LogbookEntry entry1 =
                    new LogbookEntry("Analyse",
                            LocalDateTime.of(2020, 2, 1, 10, 15), //1,75 * 10 = 17,5
                            LocalDateTime.of(2020, 2, 1, 12, 00), proj, empl, CostType.Projektleitung);
            LogbookEntry entry2 =
                    new LogbookEntry("Implementierung",
                            LocalDateTime.of(2020, 4, 1, 13, 00), //2,5 * 8 = 20
                            LocalDateTime.of(2020, 4, 1, 14, 00), proj, empl1, CostType.Entwicklung);
            LogbookEntry entry21 =
                    new LogbookEntry("Kaffee trinken",
                            LocalDateTime.of(2020, 3, 1, 14, 00),
                            LocalDateTime.of(2020, 3, 1, 15, 30), proj, empl1, CostType.Entwicklung);
            LogbookEntry entry3 =
                    new LogbookEntry("Testen",
                            LocalDateTime.of(2020, 2, 1, 15, 30), //1,5 * 8 = 12
                            LocalDateTime.of(2020, 2, 1, 17, 00), proj, empl1, CostType.Testen);
            LogbookEntry entry4 =
                    new LogbookEntry("Analyse",
                            LocalDateTime.of(2019, 2, 1, 10, 15),
                            LocalDateTime.of(2019, 2, 1, 12, 00), proj1, empl, CostType.Projektleitung);
            LogbookEntry entry5 =
                    new LogbookEntry("Implementierung",
                            LocalDateTime.of(2019, 2, 1, 13, 00),
                            LocalDateTime.of(2019, 2, 1, 15, 30), proj1, empl2, CostType.Entwicklung);
            LogbookEntry entry6 =
                    new LogbookEntry("Testen",
                            LocalDateTime.of(2019, 2, 1, 15, 30),
                            LocalDateTime.of(2019, 2, 1, 17, 00), proj1, empl2, CostType.Testen);
            LogbookEntry entry7 =
                    new LogbookEntry("Analyse",
                            LocalDateTime.of(2020, 2, 1, 10, 15),
                            LocalDateTime.of(2020, 2, 1, 12, 00), proj2, empl, CostType.Projektleitung);
            LogbookEntry entry8 =
                    new LogbookEntry("Implementierung",
                            LocalDateTime.of(2020, 2, 3, 13, 00),
                            LocalDateTime.of(2020, 2, 3, 15, 30), proj2, empl1, CostType.Entwicklung);
            LogbookEntry entry9 =
                    new LogbookEntry("Testen",
                            LocalDateTime.of(2020, 2, 1, 15, 30),
                            LocalDateTime.of(2020, 2, 1, 17, 00), proj2, empl2, CostType.Testen);
            LogbookEntry entry91 =
                    new LogbookEntry("Testen",
                            LocalDateTime.of(2020, 2, 1, 15, 30),
                            LocalDateTime.of(2020, 2, 1, 17, 00), proj2, empl3, CostType.Testen);
            LogbookEntry entry10 =
                    new LogbookEntry("Refactoring",
                            LocalDateTime.of(2020, 2, 1, 15, 30),
                            LocalDateTime.of(2020, 2, 1, 17, 00), proj2, empl3, CostType.Wartung);

            entry1 = logbookEntryLogic.syncLogbookEntry(entry1);
            entry2 = logbookEntryLogic.syncLogbookEntry(entry2);
            entry3 = logbookEntryLogic.syncLogbookEntry(entry3);
            entry4 = logbookEntryLogic.syncLogbookEntry(entry4);
            entry5 = logbookEntryLogic.syncLogbookEntry(entry5);
            entry6 = logbookEntryLogic.syncLogbookEntry(entry6);
            entry7 = logbookEntryLogic.syncLogbookEntry(entry7);
            entry8 = logbookEntryLogic.syncLogbookEntry(entry8);
            entry9 = logbookEntryLogic.syncLogbookEntry(entry9);
            entry10 = logbookEntryLogic.syncLogbookEntry(entry10);
            entry91 = logbookEntryLogic.syncLogbookEntry(entry91);
            entry21 = logbookEntryLogic.syncLogbookEntry(entry21);


            Invoice invoice = invoiceLogic.getInvoiceOfCustomer(cust,
                    LocalDateTime.of(2020, 1, 1, 15, 30),
                    LocalDateTime.of(2020, 3, 1, 17, 00));

            Invoice invoice3 = invoiceLogic.getInvoiceOfCustomer(cust,
                    LocalDateTime.of(0000, 1, 1, 15, 30),
                    LocalDateTime.of(2021, 3, 1, 17, 00));

            Invoice invoice1 = invoiceLogic.getInvoiceOfProject(proj1,
                    LocalDateTime.of(2019, 1, 1, 15, 30),
                    LocalDateTime.of(2019, 3, 1, 17, 00));
            Invoice invoice2 = invoiceLogic.getInvoiceOfProject(proj,
                    LocalDateTime.of(2000, 1, 1, 15, 30),
                    LocalDateTime.of(2022, 3, 1, 17, 00));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            String datetimeFormat = "dd.MM.yyyy HH:mm";
            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(datetimeFormat);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String availCmds = "commands: insert, update, findbyid, list";
            String availdmn = "domains: quit, employee, customer, logbook, project, criteria, invoice, evaluation," +
                    "addandremove, hourlyrate";
            System.out.println(availdmn);
            String domainCmd = promptFor(in, "");
            try {
                while (!domainCmd.equals("quit")) {
                    switch (domainCmd) {
                        case "employee":
                            System.out.println(availCmds + ", findbylastname");
                            String userCmd = promptFor(in, "");
                            switch (userCmd) {
                                case "insert":
                                    employeeLogic.syncEmployee(new Employee(
                                            promptFor(in, "firstName"),
                                            promptFor(in, "lastName"),
                                            LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"), formatter)
                                    ));
                                    break;
                                case "update":
                                    empl = employeeLogic.findEmployeeById(Long.parseLong(promptFor(in, "id")));
                                    empl.setFirstName(promptFor(in, "firstName"));
                                    empl.setLastName(promptFor(in, "lastName"));
                                    empl.setDateOfBirth(LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"), formatter));
                                    employeeLogic.syncEmployee(empl);
                                    break;
                                case "list":
                                    employeeLogic.findAllEmployees().stream().forEach(
                                            employee -> System.out.println(employee.toString()));
                                    break;
                                case "findbylastname":
                                    var employee = employeeLogic.findByLastname(promptFor(in, "Lastname"));
                                    if (employee.isPresent()) System.out.println(employee);
                                    else System.out.println("Not found");
                                    break;
                                case "findbyid":
                                    System.out.println(employeeLogic.findEmployeeById(Long.parseLong(promptFor(in, "id"))).toString());
                                    break;
                                default:
                                    System.out.println("Error invalide Command");
                                    break;
                            }
                            break;
                        case "customer":
                            System.out.println(availCmds + ", findbylastname");
                            userCmd = promptFor(in, "");
                            switch (userCmd) {
                                case "insert":
                                    customerLogic.syncCustomer(new Customer(
                                            promptFor(in, "firstName"),
                                            promptFor(in, "lastName"),
                                            LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"), formatter)
                                    ));
                                    break;
                                case "update":
                                    var custumer1 = customerLogic.findByLastname(promptFor(in, "Lastname"));
                                    if (custumer1.isPresent()) {
                                        custumer1.get().setFirstName(promptFor(in, "firstName"));
                                        custumer1.get().setLastName(promptFor(in, "lastName"));
                                        custumer1.get().setDateOfBirth(LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"), formatter));
                                        customerLogic.syncCustomer(custumer1.get());
                                    }
                                    break;
                                case "list":
                                    customerLogic.findAllCustomers().stream().forEach(
                                            customer -> System.out.println(customer.toString()));
                                    break;
                                case "findbylastname":
                                    var custumer2 = customerLogic.findByLastname(promptFor(in, "Lastname"));
                                    if (custumer2.isPresent()) {
                                        System.out.println(custumer2.get());
                                    } else System.out.println("Not found");
                                    break;
                                default:
                                    System.out.println("Error invalide Command");
                                    break;
                            }
                            break;
                        case "logbook":
                            System.out.println(availCmds);
                            userCmd = promptFor(in, "");
                            switch (userCmd) {
                                case "insert":
                                    logbookEntryLogic.syncLogbookEntry(new LogbookEntry(
                                            promptFor(in, "name"),
                                            LocalDateTime.parse(promptFor(in, "starttime " + datetimeFormat), formatterTime),
                                            LocalDateTime.parse(promptFor(in, "endtime " + datetimeFormat), formatterTime),
                                            CostType.valueOf(promptFor(in, "Projektleitung, Entwicklung, Testen, Wartung")
                                            )));
                                    break;
                                case "update":
                                    var log = logbookEntryLogic.findLogbookEntryById(Long.parseLong(promptFor(in, "id")));
                                    log.setActivity(promptFor(in, "name"));
                                    log.setStartTime(LocalDateTime.parse(promptFor(in, "starttime " + datetimeFormat), formatterTime));
                                    log.setEndTime(LocalDateTime.parse(promptFor(in, "endtime " + datetimeFormat), formatterTime));
                                    log.setCostType(CostType.valueOf(promptFor(in, "Projektleitung, Entwicklung, Testen, Wartung")));
                                    logbookEntryLogic.syncLogbookEntry(log);
                                    break;
                                case "list":
                                    logbookEntryLogic.findAll().stream().forEach(
                                            l -> System.out.println(l.toString()));
                                    break;

                                case "findbyid":
                                    System.out.println(logbookEntryLogic.findLogbookEntryById(Long.parseLong(promptFor(in, "id"))));
                                    break;
                                default:
                                    System.out.println("Error invalide Command");
                                    break;
                            }
                            break;

                        case "project":
                            System.out.println(availCmds + ", findbyname, getallemployees, getallprojectsofcustomer");
                            userCmd = promptFor(in, "");
                            switch (userCmd) {
                                case "insert":
                                    projectLogic.syncProject(new Project(
                                            promptFor(in, "name")
                                    ));
                                    break;
                                case "getallemployees":
                                    var projject3 = projectLogic.findByName((promptFor(in, "name")));
                                    if (projject3.isPresent()) {
                                        var liste = employeeLogic.findAllEmployeesOfProject(projject3.get());
                                        liste.forEach(employee -> System.out.println(employee));
                                    }
                                    break;
                                case "getallprojectsofcustomer":
                                    var custo = customerLogic.findByLastname((promptFor(in, "name")));
                                    if (custo.isPresent()) {
                                        var list = projectLogic.findAllProjectsOfCustomer(custo.get());
                                        list.forEach(pro -> System.out.println(pro));
                                    }
                                    break;
                                case "update":
                                    var projject1 = projectLogic.findByName((promptFor(in, "name")));
                                    if (projject1.isPresent()) {
                                        projject1.get().setName(promptFor(in, "name"));
                                        projectLogic.syncProject(projject1.get());
                                    } else System.out.println("Not found");

                                    break;
                                case "list":
                                    projectLogic.findAllProjects().stream().forEach(
                                            project -> System.out.println(project.toString()));
                                    break;
                                case "findbyname":
                                    var projject2 = projectLogic.findByName((promptFor(in, "name")));
                                    if (projject2.isPresent()) System.out.println(projject2.get());
                                    else System.out.println("Not found");
                                    break;
                                default:
                                    System.out.println("Error invalide Command");
                                    break;
                            }
                            break;

                        case "invoice":
                            System.out.println("invoicebyproject, invoicebycustomer");
                            userCmd = promptFor(in, "");
                            switch (userCmd) {
                                case "invoicebycustomer":
                                    var customer = customerLogic.findByLastname(promptFor(in, "Lastname"));
                                    if (customer.isPresent()) {
                                        var startTime = LocalDateTime.parse(promptFor(in, "starttime  " + datetimeFormat), formatterTime);
                                        var endTime = LocalDateTime.parse(promptFor(in, "endtime  " + datetimeFormat), formatterTime);
                                        System.out.println(invoiceLogic.getInvoiceOfCustomer(customer.get(), startTime, endTime));
                                    } else System.out.println("Not Found");
                                    break;
                                case "invoicebyproject":
                                    var project = projectLogic.findByName(promptFor(in, "Name"));
                                    if (project.isPresent()) {
                                        var startTime = LocalDateTime.parse(promptFor(in, "starttime " + datetimeFormat), formatterTime);
                                        var endTime = LocalDateTime.parse(promptFor(in, "endtime " + datetimeFormat), formatterTime);
                                        System.out.println(invoiceLogic.getInvoiceOfProject(project.get(), startTime, endTime));
                                    } else System.out.println("Not Found");
                                    break;
                                default:
                                    System.out.println("Error invalide Command");
                                    break;
                            }
                            break;

                        case "evaluation":
                            System.out.println("gettimeofemplyoeeworking, getcostsofproject, gettimeofemplyoeeworkinghoursonprojects");
                            userCmd = promptFor(in, "");
                            switch (userCmd) {
                                case "gettimeofemplyoeeworking":
                                    var employee = employeeLogic.findByLastname(promptFor(in, "Lastname"));
                                    if (employee.isPresent()) {
                                        var res = evaluationsLogic.getTimeOfEmplyoeeWorking(employee.get(),
                                                LocalDateTime.parse(promptFor(in, "starttime  " + datetimeFormat), formatterTime),
                                                LocalDateTime.parse(promptFor(in, "endtime  " + datetimeFormat), formatterTime));
                                        System.out.println(res);
                                    } else System.out.println("Not Found");
                                    break;
                                case "getcostsofproject":
                                    var project = projectLogic.findByName(promptFor(in, "Name"));
                                    if (project.isPresent()) {
                                        var res = evaluationsLogic.getCostsOfProject(project.get());
                                        System.out.println(res);
                                    } else System.out.println("Not Found");
                                    break;
                                case "gettimeofemplyoeeworkinghoursonprojects":
                                    var employee1 = employeeLogic.findByLastname(promptFor(in, "Lastname"));
                                    if (employee1.isPresent()) {
                                        var res = evaluationsLogic.getTimeOfEmplyoeeWorkingHoursOnProjects(employee1.get());
                                        System.out.println(res);
                                    } else System.out.println("Not Found");
                                    break;
                                default:
                                    System.out.println("Error invalide Command");
                                    break;
                            }
                            break;
                        case "addandremove":
                            System.out.println("addemptopro, rememptopro, addcustopro, remcustopro, " +
                                    "addemptolog, rememptolog, addlogtopro, remlogtopro");
                            userCmd = promptFor(in, "");
                            switch (userCmd) {
                                case "addemptopro":
                                    var employee = employeeLogic.findByLastname(promptFor(in, "Lastname"));
                                    if (!employee.isPresent()) {
                                        break;
                                    }
                                    var project = projectLogic.findByName(promptFor(in, "Name"));
                                    if (!project.isPresent()) {
                                        break;
                                    }
                                    projectLogic.AddEmployeeToProject(project.get(), employee.get());
                                    break;
                                case "rememptopro":
                                    employee = employeeLogic.findByLastname(promptFor(in, "Lastname"));
                                    if (!employee.isPresent()) {
                                        break;
                                    }
                                    project = projectLogic.findByName(promptFor(in, "Name"));
                                    if (!project.isPresent()) {
                                        break;
                                    }
                                    projectLogic.RemoveEmployeeToProject(project.get(), employee.get());
                                    break;
                                case "addcustopro":
                                    var customer = customerLogic.findByLastname(promptFor(in, "Lastname"));
                                    if (!customer.isPresent()) {
                                        break;
                                    }
                                    project = projectLogic.findByName(promptFor(in, "Name"));
                                    if (!project.isPresent()) {
                                        break;
                                    }
                                    customerLogic.AddCustomerToProject(project.get(), customer.get());
                                    break;
                                case "remcustopro":
                                    customer = customerLogic.findByLastname(promptFor(in, "Lastname"));
                                    if (!customer.isPresent()) {
                                        break;
                                    }
                                    project = projectLogic.findByName(promptFor(in, "Name"));
                                    if (!project.isPresent()) {
                                        break;
                                    }
                                    customerLogic.RemoveCustomerFromProject(project.get(), customer.get());
                                    break;
                                case "addemptolog":
                                    employee = employeeLogic.findByLastname(promptFor(in, "Lastname"));
                                    if (!employee.isPresent()) {
                                        break;
                                    }
                                    var log = logbookEntryLogic.findByActivity(promptFor(in, "Activity"));
                                    if (!log.isPresent()) {
                                        break;
                                    }
                                    logbookEntryLogic.addEmployeeToLogbookEntry(log.get(), employee.get());
                                    break;
                                case "rememptolog":
                                    employee = employeeLogic.findByLastname(promptFor(in, "Lastname"));
                                    if (!employee.isPresent()) {
                                        break;
                                    }
                                    log = logbookEntryLogic.findByActivity(promptFor(in, "Activity"));
                                    if (!log.isPresent()) {
                                        break;
                                    }
                                    logbookEntryLogic.removeEmployeeToLogbookEntry(log.get(), employee.get());
                                    break;
                                case "addlogtopro":
                                    project = projectLogic.findByName(promptFor(in, "Name"));
                                    if (!project.isPresent()) {
                                        break;
                                    }
                                    log = logbookEntryLogic.findByActivity(promptFor(in, "Activity"));
                                    if (!log.isPresent()) {
                                        break;
                                    }
                                    logbookEntryLogic.addProjectToLogbookEntry(log.get(), project.get());
                                    break;
                                case "remlogtopro":
                                    project = projectLogic.findByName(promptFor(in, "Name"));
                                    if (!project.isPresent()) {
                                        break;
                                    }
                                    log = logbookEntryLogic.findByActivity(promptFor(in, "Activity"));
                                    if (!log.isPresent()) {
                                        break;
                                    }
                                    logbookEntryLogic.removeProjectToLogbookEntry(log.get(), project.get());
                                    break;
                                default:
                                    System.out.println("Error invalide Command");
                                    break;
                            }
                            break;
                        case "hourlyrate":
                            System.out.println("list, insert");
                            userCmd = promptFor(in, "");
                            switch (userCmd) {
                                case "list":
                                    hourlyRateLogic.findAllHourlyRates().forEach(hourlyRate -> System.out.println(hourlyRate));
                                    break;
                                case "insert":
                                    hourlyRateLogic.syncHourlyRate(new HourlyRate(
                                            Double.valueOf(promptFor(in, "rate")),
                                            EmployeeCategory.valueOf(promptFor(in, "Projektleiter, SeniorDeveloper, JuniorDeveloper")),
                                            Integer.valueOf(promptFor(in, "year"))
                                    ));
                                    break;
                                default:
                                    System.out.println("Error invalide Command");
                                    break;
                            }
                            break;
                        default:
                            System.out.println("Error invalide Command");
                            break;
                    }
                    System.out.println(availdmn);
                    domainCmd = promptFor(in, "");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
