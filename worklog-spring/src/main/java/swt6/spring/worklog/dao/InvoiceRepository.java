package swt6.spring.worklog.dao;

import swt6.spring.worklog.domain.Customer;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.dto.Invoice;

import java.time.LocalDateTime;

public interface InvoiceRepository {
    Invoice getInvoiceForCustomer(Customer customer, LocalDateTime from, LocalDateTime to);

    Invoice getInvoiceForProject(Project project, LocalDateTime from, LocalDateTime to);
}
