package com.banking.loan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Contains the business rule for loan decisions.
 * The controller only handles HTTP, and this class decides Approved or Rejected.
 */
@Service
public class LoanService {

    private static final Logger log = LoggerFactory.getLogger(LoanService.class);

    // BigDecimal is used for money because double can give rounding errors.
    private static final BigDecimal THRESHOLD_RATIO = new BigDecimal("0.30");

    private final LoanCheckRepository repository;

    public LoanService(LoanCheckRepository repository) {
        this.repository = repository;
    }

    /**
     * Approves the loan only if salary is strictly greater than 30% of the requested amount.
     * Exactly 30% is Rejected.
     *
     * @param salary          monthly salary of the applicant
     * @param requestedAmount loan amount requested
     * @return "Approved" or "Rejected"
     */
    public String checkLoan(BigDecimal salary, BigDecimal requestedAmount) {
        BigDecimal threshold = requestedAmount.multiply(THRESHOLD_RATIO);

        // compareTo > 0 means "strictly greater than"
        boolean approved = salary.compareTo(threshold) > 0;
        String decision = approved ? "Approved" : "Rejected";

        // Save every check so there is a history.
        repository.save(new LoanCheck(salary, requestedAmount, decision));

        // Only the decision is logged. Salary is private data and must not appear in logs.
        log.info("Loan check completed. Decision: {}", decision);
        return decision;
    }
}