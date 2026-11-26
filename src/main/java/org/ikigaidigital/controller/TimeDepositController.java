package org.ikigaidigital.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ikigaidigital.service.TimeDepositService;
import org.ikigaidigital.domain.TimeDeposit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/time-deposits")
@Tag(name = "Time Deposits", description = "Time deposit balance operations")
public class TimeDepositController {
    private final TimeDepositService service;

    public TimeDepositController(TimeDepositService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Retrieve all time deposits", description = "Returns every time deposit and its withdrawals")
    public List<TimeDeposit> getAll() {
        return service.findAll();
    }

    @PutMapping("/update-balances")
    @Operation(summary = "Update all time deposit balances", description = "Applies the applicable monthly interest to every time deposit")
    public ResponseEntity<String> updateBalances() {
        service.updateAllBalances();
        return ResponseEntity.accepted()
                .body("Time deposit balance update has been accepted");
    }
}
