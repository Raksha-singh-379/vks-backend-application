package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanWatapDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanWatapDetails.class);
        LoanWatapDetails loanWatapDetails1 = new LoanWatapDetails();
        loanWatapDetails1.setId(1L);
        LoanWatapDetails loanWatapDetails2 = new LoanWatapDetails();
        loanWatapDetails2.setId(loanWatapDetails1.getId());
        assertThat(loanWatapDetails1).isEqualTo(loanWatapDetails2);
        loanWatapDetails2.setId(2L);
        assertThat(loanWatapDetails1).isNotEqualTo(loanWatapDetails2);
        loanWatapDetails1.setId(null);
        assertThat(loanWatapDetails1).isNotEqualTo(loanWatapDetails2);
    }
}
