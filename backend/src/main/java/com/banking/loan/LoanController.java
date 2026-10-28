package com.banking.loan;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoint for loan eligibility checks.
 * Receives the request, passes it to the service, and returns the decision as JSON.
 */
@RestController
@RequestMapping("/api/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * POST /api/loan/check
     * Body: {"salary": 40000, "requestedAmount": 100000}
     * Returns: {"decision": "Approved"} or {"decision": "Rejected"}
     * Invalid input (@Valid fails) returns 400 through GlobalExceptionHandler.
     */
    @PostMapping("/check")
    public ResponseEntity<LoanResponse> checkLoan(@Valid @RequestBody LoanRequest request) {
        String decision = loanService.checkLoan(request.salary(), request.requestedAmount());
        return ResponseEntity.ok(new LoanResponse(decision));
    }
}