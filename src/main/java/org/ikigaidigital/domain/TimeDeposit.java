package org.ikigaidigital.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TIME_DEPOSITS")
public class TimeDeposit {
    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PLAN_TYPE", nullable = false)
    private PlanType planType;

    @Column(name = "BALANCE", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(name = "DAYS", nullable = false)
    private Integer days;

    @OneToMany(mappedBy = "timeDeposit")
    private List<Withdrawal> withdrawals = new ArrayList<>();

    protected TimeDeposit() {
    }

    public TimeDeposit(int id, PlanType planType, BigDecimal balance, int days) {
        this.id = id;
        this.planType = planType;
        this.balance = balance;
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getDays() {
        return days;
    }

    public List<Withdrawal> getWithdrawals() {
        return withdrawals;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
