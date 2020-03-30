package swt6.spring.worklog.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.InvoiceRepository;
import swt6.spring.worklog.domain.Customer;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.dto.Invoice;
import swt6.spring.worklog.logic.interf.InvoiceLogic;

import java.time.LocalDateTime;

@Service("invoiceLogic")
@Primary
@Transactional
public class InvoiceLogicImpl implements InvoiceLogic {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice getInvoiceOfProject(Project project, LocalDateTime from, LocalDateTime to) {
        return invoiceRepository.getInvoiceForProject(project, from, to);
    }

    @Override
    public Invoice getInvoiceOfCustomer(Customer customer, LocalDateTime from, LocalDateTime to) {
        return invoiceRepository.getInvoiceForCustomer(customer, from, to);
    }
}
