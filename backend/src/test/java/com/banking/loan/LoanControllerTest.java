package com.banking.loan;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
@Import(SecurityConfig.class)
@WithMockUser(username = "officer", roles = "LOAN_OFFICER")
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoanService loanService;

    @Test
    void returnsApprovedDecision() throws Exception {
        when(loanService.checkLoan(new BigDecimal("40000"), new BigDecimal("100000")))
                .thenReturn("Approved");

        mockMvc.perform(post("/api/loan/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"salary\":40000,\"requestedAmount\":100000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.decision").value("Approved"));
    }

    @Test
    void returnsRejectedDecision() throws Exception {
        when(loanService.checkLoan(new BigDecimal("20000"), new BigDecimal("100000")))
                .thenReturn("Rejected");

        mockMvc.perform(post("/api/loan/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"salary\":20000,\"requestedAmount\":100000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.decision").value("Rejected"));
    }

    @Test
    void returns400ForNegativeSalary() throws Exception {
        mockMvc.perform(post("/api/loan/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"salary\":-5,\"requestedAmount\":100000}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void returns400ForMissingField() throws Exception {
        mockMvc.perform(post("/api/loan/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"salary\":40000}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithAnonymousUser
    void returns401WhenNotLoggedIn() throws Exception {
        mockMvc.perform(post("/api/loan/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"salary\":40000,\"requestedAmount\":100000}"))
                .andExpect(status().isUnauthorized());
    }
}