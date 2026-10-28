// Saves and reads LoanCheck rows. Spring writes the SQL for us.
package com.banking.loan;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanCheckRepository extends JpaRepository<LoanCheck, Long> {
}