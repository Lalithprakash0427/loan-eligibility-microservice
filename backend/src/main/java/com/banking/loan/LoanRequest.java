package com.banking.loan;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record LoanRequest(
        @NotNull(message = "salary is required")
        @Positive(message = "salary must be greater than 0")
        BigDecimal salary,

        @NotNull(message = "requestedAmount is required")
        @Positive(message = "requestedAmount must be greater than 0")
        BigDecimal requestedAmount
) {}