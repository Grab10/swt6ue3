package swt6.spring.worklog.test;

import org.springframework.beans.factory.annotation.Autowired;
import swt6.spring.worklog.domain.*;

import swt6.spring.worklog.dto.Invoice;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SuppressWarnings("Duplicates")
public class LogicTest {


    public static void main(String[] args) {

        printSeparator(60);
        printTitle("testBusinessLogicWithSpringDataRepositories", 60);
        printSeparator(60);
//        Helper helper = new Helper();
//        helper.generateData();
    }


//    private static class Helper {
//        @Autowired
//        private WorkLogFacade workLog;
//
//        public void generateData() {
//
//
//            var empl = new Employee("Hans", "Huber",
//                    LocalDate.of(1980, 12, 8));
//            empl.setAddress(new Address("4064", "Oftering", "Landstraße 1"));
//            var rate1 = new HourlyRate(10, EmployeeCategory.Projektleiter, 2020);
//            rate1 = workLog.syncHourlyRate(rate1);
//            empl.setHourlyRate(rate1);
//
//            var empl1 = new Employee("Franz", "Mayr",
//                    LocalDate.of(1990, 1, 5));
//            empl1.setAddress(new Address("4062", "Kirchberg", "Hauptstraße 1"));
//            var rate2 = new HourlyRate(8, EmployeeCategory.SeniorDeveloper, 2020);
//            rate2 = workLog.syncHourlyRate(rate2);
//            empl1.setHourlyRate(rate2);
//
//            var rate = new HourlyRate(7, EmployeeCategory.JuniorDeveloper, 2020);
//            rate = workLog.syncHourlyRate(rate);
//
//            var empl2 = new Employee("Karl", "Huemer",
//                    LocalDate.of(1987, 3, 21));
//            empl2.setAddress(new Address("4062", "Kirchberg", "Sonnenblumenstraße 2"));
//            empl2.setHourlyRate(rate);
//
//            var empl3 = new Employee("Max", "Horn",
//                    LocalDate.of(1970, 1, 21));
//            empl3.setAddress(new Address("4072", "Alkoven", "Falkenweg 55"));
//            empl3.setHourlyRate(rate);
//
//
//            workLog.syncEmployee(empl);
//            workLog.syncEmployee(empl1);
//            workLog.syncEmployee(empl2);
//            workLog.syncEmployee(empl3);
//
//            var cust = new Customer("Markus", "Schmid",
//                    LocalDate.of(1950, 8, 15),
//                    new Address("4072", "Alkoven", "Gartenstraße 12"));
//            var cust1 = new Customer("Kaiser", "Scholz",
//                    LocalDate.of(1960, 2, 5),
//                    new Address("4072", "Alkoven", "Falkenweg 55"));
//            var cust2 = new Customer("Gerlach", "Graf",
//                    LocalDate.of(1960, 5, 2),
//                    new Address("4070", "Eferding", "Erlenweg 4"));
//
//
//            cust = workLog.syncCustomer(cust);
//            cust1 = workLog.syncCustomer(cust1);
//            cust2 = workLog.syncCustomer(cust2);
//
//
//            var proj = new Project("Youtube 2.0");
//            proj.addMember(empl);
//            proj.addMember(empl1);
//            proj.setCustomer(cust);
//
//            var proj1 = new Project("Google 2.0");
//            proj1.addMember(empl);
//            proj1.addMember(empl2);
//            proj1.setCustomer(cust);
//
//            var proj2 = new Project("Gitlab 2.0");
//            proj2.addMember(empl);
//            proj2.addMember(empl1);
//            proj2.addMember(empl2);
//            proj2.addMember(empl3);
//            proj2.setCustomer(cust1);
//
//            proj = workLog.syncProject(proj);
//            proj1 = workLog.syncProject(proj1);
//            proj2 = workLog.syncProject(proj2);
//
//            LogbookEntry entry1 =
//                    new LogbookEntry("Analyse",
//                            LocalDateTime.of(2020, 2, 1, 10, 15), //1,75 * 10 = 17,5
//                            LocalDateTime.of(2020, 2, 1, 12, 00), proj, empl, CostType.Projektleitung);
//            LogbookEntry entry2 =
//                    new LogbookEntry("Implementierung",
//                            LocalDateTime.of(2020, 4, 1, 13, 00), //2,5 * 8 = 20
//                            LocalDateTime.of(2020, 4, 1, 14, 00), proj, empl1, CostType.Entwicklung);
//            LogbookEntry entry21 =
//                    new LogbookEntry("Kaffee trinken",
//                            LocalDateTime.of(2020, 3, 1, 14, 00),
//                            LocalDateTime.of(2020, 3, 1, 15, 30), proj, empl1, CostType.Entwicklung);
//            LogbookEntry entry3 =
//                    new LogbookEntry("Testen",
//                            LocalDateTime.of(2020, 2, 1, 15, 30), //1,5 * 8 = 12
//                            LocalDateTime.of(2020, 2, 1, 17, 00), proj, empl1, CostType.Testen);
//            LogbookEntry entry4 =
//                    new LogbookEntry("Analyse",
//                            LocalDateTime.of(2019, 2, 1, 10, 15),
//                            LocalDateTime.of(2019, 2, 1, 12, 00), proj1, empl, CostType.Projektleitung);
//            LogbookEntry entry5 =
//                    new LogbookEntry("Implementierung",
//                            LocalDateTime.of(2019, 2, 1, 13, 00),
//                            LocalDateTime.of(2019, 2, 1, 15, 30), proj1, empl2, CostType.Entwicklung);
//            LogbookEntry entry6 =
//                    new LogbookEntry("Testen",
//                            LocalDateTime.of(2019, 2, 1, 15, 30),
//                            LocalDateTime.of(2019, 2, 1, 17, 00), proj1, empl2, CostType.Testen);
//            LogbookEntry entry7 =
//                    new LogbookEntry("Analyse",
//                            LocalDateTime.of(2020, 2, 1, 10, 15),
//                            LocalDateTime.of(2020, 2, 1, 12, 00), proj2, empl, CostType.Projektleitung);
//            LogbookEntry entry8 =
//                    new LogbookEntry("Implementierung",
//                            LocalDateTime.of(2020, 2, 3, 13, 00),
//                            LocalDateTime.of(2020, 2, 3, 15, 30), proj2, empl1, CostType.Entwicklung);
//            LogbookEntry entry9 =
//                    new LogbookEntry("Testen",
//                            LocalDateTime.of(2020, 2, 1, 15, 30),
//                            LocalDateTime.of(2020, 2, 1, 17, 00), proj2, empl2, CostType.Testen);
//            LogbookEntry entry91 =
//                    new LogbookEntry("Testen",
//                            LocalDateTime.of(2020, 2, 1, 15, 30),
//                            LocalDateTime.of(2020, 2, 1, 17, 00), proj2, empl3, CostType.Testen);
//            LogbookEntry entry10 =
//                    new LogbookEntry("Refactoring",
//                            LocalDateTime.of(2020, 2, 1, 15, 30),
//                            LocalDateTime.of(2020, 2, 1, 17, 00), proj2, empl3, CostType.Wartung);
//
//            entry1 = workLog.syncLogbookEntry(entry1);
//            entry2 = workLog.syncLogbookEntry(entry2);
//            entry3 = workLog.syncLogbookEntry(entry3);
//            entry4 = workLog.syncLogbookEntry(entry4);
//            entry5 = workLog.syncLogbookEntry(entry5);
//            entry6 = workLog.syncLogbookEntry(entry6);
//            entry7 = workLog.syncLogbookEntry(entry7);
//            entry8 = workLog.syncLogbookEntry(entry8);
//            entry9 = workLog.syncLogbookEntry(entry9);
//            entry10 = workLog.syncLogbookEntry(entry10);
//            entry91 = workLog.syncLogbookEntry(entry91);
//            entry21 = workLog.syncLogbookEntry(entry21);
//
//
//            Invoice invoice = workLog.getInvoiceOfCustomer(cust,
//                    LocalDateTime.of(2020, 1, 1, 15, 30),
//                    LocalDateTime.of(2020, 3, 1, 17, 00));
//
//            Invoice invoice3 = workLog.getInvoiceOfCustomer(cust,
//                    LocalDateTime.of(0000, 1, 1, 15, 30),
//                    LocalDateTime.of(2021, 3, 1, 17, 00));
//
//
//            Invoice invoice1 = workLog.getInvoiceOfProject(proj1,
//                    LocalDateTime.of(2019, 1, 1, 15, 30),
//                    LocalDateTime.of(2019, 3, 1, 17, 00));
//            Invoice invoice2 = workLog.getInvoiceOfProject(proj,
//                    LocalDateTime.of(2000, 1, 1, 15, 30),
//                    LocalDateTime.of(2022, 3, 1, 17, 00));
//
//            System.out.println(invoice);
//            System.out.println(invoice1);
//            System.out.println(invoice3);
//            System.out.println(invoice2);
//
//            System.out.println(invoice2);
//        }
//    }
}
