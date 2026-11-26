package org.ikigaidigital.service;

import org.ikigaidigital.domain.TimeDeposit;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class TimeDepositCalculator {
    private static final int NO_INTEREST_DAYS = 30;
    private static final int PREMIUM_START_DAYS = 45;
    private static final int STUDENT_END_DAYS = 365;

    private static final BigDecimal MONTHS_PER_YEAR = new BigDecimal("12");
    private static final BigDecimal STUDENT_RATE = new BigDecimal("0.03");
    private static final BigDecimal PREMIUM_RATE = new BigDecimal("0.05");
    private static final BigDecimal BASIC_RATE = new BigDecimal("0.01");

    public void updateBalance(List<TimeDeposit> deposits) {
        for (TimeDeposit deposit : deposits) {
            BigDecimal interest = calculateInterest(deposit);
            BigDecimal newBalance = deposit.getBalance()
                    .add(interest)
                    .setScale(2, RoundingMode.HALF_UP);
            deposit.setBalance(newBalance);
        }
    }

    private BigDecimal calculateInterest(TimeDeposit deposit) {
        int days = deposit.getDays();

        if (days <= NO_INTEREST_DAYS) {
            return BigDecimal.ZERO;
        }

        BigDecimal annualRate = switch (deposit.getPlanType()) {
            case BASIC -> BASIC_RATE;
            case STUDENT -> days <= STUDENT_END_DAYS ? STUDENT_RATE : null;
            case PREMIUM -> days > PREMIUM_START_DAYS ? PREMIUM_RATE : null;
        };

        if (annualRate == null) {
            return BigDecimal.ZERO;
        }

        return deposit.getBalance()
                .multiply(annualRate)
                .divide(MONTHS_PER_YEAR, 2, RoundingMode.HALF_UP);
    }
}
