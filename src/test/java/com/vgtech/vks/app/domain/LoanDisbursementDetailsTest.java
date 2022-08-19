package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanDisbursementDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanDisbursementDetails.class);
        LoanDisbursementDetails loanDisbursementDetails1 = new LoanDisbursementDetails();
        loanDisbursementDetails1.setId(1L);
        LoanDisbursementDetails loanDisbursementDetails2 = new LoanDisbursementDetails();
        loanDisbursementDetails2.setId(loanDisbursementDetails1.getId());
        assertThat(loanDisbursementDetails1).isEqualTo(loanDisbursementDetails2);
        loanDisbursementDetails2.setId(2L);
        assertThat(loanDisbursementDetails1).isNotEqualTo(loanDisbursementDetails2);
        loanDisbursementDetails1.setId(null);
        assertThat(loanDisbursementDetails1).isNotEqualTo(loanDisbursementDetails2);
    }
}
