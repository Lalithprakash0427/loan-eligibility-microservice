package com.banking.loan;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_checks")
public class LoanCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal salary;
    private BigDecimal requestedAmount;
    private String decision;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Needed by Spring/JPA. Do not delete.
    protected LoanCheck() {
    }

    public LoanCheck(BigDecimal salary, BigDecimal requestedAmount, String decision) {
        this.salary = salary;
        this.requestedAmount = requestedAmount;
        this.decision = decision;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public String getDecision() {
        return decision;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}