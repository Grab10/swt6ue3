package swt6.spring.worklog.dao;

import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Customer;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Project;
import swt6.spring.worklog.dto.Invoice;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Invoice getInvoiceForCustomer(Customer customer, LocalDateTime from, LocalDateTime to) {
        Invoice invoice = null;
        TypedQuery<LogbookEntry> qry = em.createQuery("select log from LogbookEntry log where log.project.customer = :cust " +
                        "and log.startTime >= :f " +
                        "and log.endTime <= :t",
                LogbookEntry.class);

        qry.setParameter("cust", customer);
        qry.setParameter("f", from);
        qry.setParameter("t", to);
        var results = qry.getResultList();
        invoice = new Invoice(results);
        return invoice;
    }

    @Override
    public Invoice getInvoiceForProject(Project project, LocalDateTime from, LocalDateTime to) {
        Invoice invoice = null;
        TypedQuery<LogbookEntry> qry = em.createQuery("select log from LogbookEntry log where log.project = :pro " +
                        "and log.startTime >= :f " +
                        "and log.endTime <= :t",
                LogbookEntry.class);
        qry.setParameter("pro", project);
        qry.setParameter("f", from);
        qry.setParameter("t", to);
        List<LogbookEntry> results = qry.getResultList();
        invoice = new Invoice(results);
        return invoice;
    }
}
