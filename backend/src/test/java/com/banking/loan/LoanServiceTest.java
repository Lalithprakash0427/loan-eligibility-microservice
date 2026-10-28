package com.banking.loan;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class LoanServiceTest {

    private final LoanService loanService = new LoanService(mock(LoanCheckRepository.class));

    @Test
    void approvesWhenSalaryIsMoreThan30Percent() {
        String result = loanService.checkLoan(new BigDecimal("40000"), new BigDecimal("100000"));
        assertEquals("Approved", result);
    }

    @Test
    void rejectsWhenSalaryIsLessThan30Percent() {
        String result = loanService.checkLoan(new BigDecimal("20000"), new BigDecimal("100000"));
        assertEquals("Rejected", result);
    }

    @Test
    void rejectsWhenSalaryIsExactly30Percent() {
        String result = loanService.checkLoan(new BigDecimal("30000"), new BigDecimal("100000"));
        assertEquals("Rejected", result);
    }
}