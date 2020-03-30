package swt6.spring.worklog.logic.interf;

import swt6.spring.worklog.domain.HourlyRate;

import java.util.List;

public interface HourlyRateLogic {
    HourlyRate syncHourlyRate(HourlyRate hourlyRate);

    List<HourlyRate> findAllHourlyRates();
}
