package org.ikigaidigital;

import org.ikigaidigital.domain.PlanType;
import org.ikigaidigital.domain.TimeDeposit;
import org.ikigaidigital.service.TimeDepositCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeDepositCalculatorTest {
    private final TimeDepositCalculator calculator = new TimeDepositCalculator();

    @Test
    public void updateBalance_Test() {
        TimeDepositCalculator calc = new TimeDepositCalculator();
        List<TimeDeposit> plans = List.of(
            new TimeDeposit(1, PlanType.BASIC, new BigDecimal("1234567.00"), 45)
        );
        calc.updateBalance(plans);

        assertThat(plans.get(0).getBalance()).isEqualByComparingTo("1235595.81");
    }

    @Test
    public void basicPlanEarnsInterestAfterThirtyDays() {
        TimeDeposit deposit = deposit(PlanType.BASIC, "1200.00", 31);

        calculator.updateBalance(List.of(deposit));

        assertThat(deposit.getBalance()).isEqualByComparingTo("1201.00");
    }

    @Test
    public void noPlanEarnsInterestDuringFirstThirtyDays() {
        TimeDeposit deposit = deposit(PlanType.PREMIUM, "1200.00", 30);

        calculator.updateBalance(List.of(deposit));

        assertThat(deposit.getBalance()).isEqualByComparingTo("1200.00");
    }

    @Test
    public void studentPlanEarnsInterestForOneYear() {
        TimeDeposit deposit = deposit(PlanType.STUDENT, "1200.00", 365);

        calculator.updateBalance(List.of(deposit));

        assertThat(deposit.getBalance()).isEqualByComparingTo("1203.00");
    }

    @Test
    public void studentPlanStopsEarningInterestAfterOneYear() {
        TimeDeposit deposit = deposit(PlanType.STUDENT, "1200.00", 366);

        calculator.updateBalance(List.of(deposit));

        assertThat(deposit.getBalance()).isEqualByComparingTo("1200.00");
    }

    @Test
    public void premiumPlanStartsEarningAfterFortyFiveDays() {
        TimeDeposit atFortyFiveDays = deposit(PlanType.PREMIUM, "1200.00", 45);
        TimeDeposit afterFortyFiveDays = deposit(PlanType.PREMIUM, "1200.00", 46);

        calculator.updateBalance(List.of(atFortyFiveDays, afterFortyFiveDays));

        assertThat(atFortyFiveDays.getBalance()).isEqualByComparingTo("1200.00");
        assertThat(afterFortyFiveDays.getBalance()).isEqualByComparingTo("1205.00");
    }

    private TimeDeposit deposit(PlanType planType, String balance, int days) {
        return new TimeDeposit(1, planType, new BigDecimal(balance), days);
    }
}
