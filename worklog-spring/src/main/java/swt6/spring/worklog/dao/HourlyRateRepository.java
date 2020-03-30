package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.spring.worklog.domain.HourlyRate;

public interface HourlyRateRepository extends JpaRepository<HourlyRate, Long> {
}
