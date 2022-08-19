package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanDetails.class);
        LoanDetails loanDetails1 = new LoanDetails();
        loanDetails1.setId(1L);
        LoanDetails loanDetails2 = new LoanDetails();
        loanDetails2.setId(loanDetails1.getId());
        assertThat(loanDetails1).isEqualTo(loanDetails2);
        loanDetails2.setId(2L);
        assertThat(loanDetails1).isNotEqualTo(loanDetails2);
        loanDetails1.setId(null);
        assertThat(loanDetails1).isNotEqualTo(loanDetails2);
    }
}
