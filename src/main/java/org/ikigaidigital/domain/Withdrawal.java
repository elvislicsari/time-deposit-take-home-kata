package org.ikigaidigital.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "WITHDRAWALS")
public class Withdrawal {
    @Id
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TIME_DEPOSIT_ID", nullable = false)
    @JsonIgnore
    private TimeDeposit timeDeposit;

    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "WITHDRAWAL_DATE", nullable = false)
    private LocalDate date;

    protected Withdrawal() {
    }

    public Withdrawal(int id, int timeDepositId, BigDecimal amount, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
