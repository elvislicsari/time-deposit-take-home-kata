package org.ikigaidigital.service;

import org.ikigaidigital.domain.TimeDeposit;
import org.ikigaidigital.repository.TimeDepositJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TimeDepositService {
    private final TimeDepositJpaRepository repository;
    private final TimeDepositCalculator calculator;

    public TimeDepositService(TimeDepositJpaRepository repository,
                              TimeDepositCalculator calculator) {
        this.repository = repository;
        this.calculator = calculator;
    }

    @Transactional
    public List<TimeDeposit> updateAllBalances() {
        List<TimeDeposit> deposits = repository.findAll();
        calculator.updateBalance(deposits);
        return repository.saveAll(deposits);
    }

    @Transactional(readOnly = true)
    public List<TimeDeposit> findAll() {
        return repository.findAll();
    }
}
