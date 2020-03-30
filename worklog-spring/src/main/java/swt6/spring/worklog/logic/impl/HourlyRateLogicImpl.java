package swt6.spring.worklog.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.HourlyRateRepository;
import swt6.spring.worklog.domain.HourlyRate;
import swt6.spring.worklog.logic.interf.HourlyRateLogic;

import java.util.List;

@Service("hourlyRateLogic")
@Primary
@Transactional
public class HourlyRateLogicImpl implements HourlyRateLogic {

    @Autowired
    private HourlyRateRepository hourlyRateRepository;

    @Override
    public HourlyRate syncHourlyRate(HourlyRate hourlyRate) {
        return hourlyRateRepository.saveAndFlush(hourlyRate);
    }

    @Override
    public List<HourlyRate> findAllHourlyRates() {
        return hourlyRateRepository.findAll();
    }

}
