package org.ikigaidigital.repository;

import org.ikigaidigital.domain.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalJpaRepository extends JpaRepository<Withdrawal, Integer> {
}

