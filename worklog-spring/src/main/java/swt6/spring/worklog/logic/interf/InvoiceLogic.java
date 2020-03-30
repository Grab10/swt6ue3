package swt6.spring.worklog.logic.interf;

import swt6.spring.worklog.domain.Customer;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.dto.Invoice;

import java.time.LocalDateTime;

public interface InvoiceLogic {
    Invoice getInvoiceOfProject(Project project, LocalDateTime from, LocalDateTime to);

    Invoice getInvoiceOfCustomer(Customer customer, LocalDateTime from, LocalDateTime to);
}
