package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanDemandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanDemand.class);
        LoanDemand loanDemand1 = new LoanDemand();
        loanDemand1.setId(1L);
        LoanDemand loanDemand2 = new LoanDemand();
        loanDemand2.setId(loanDemand1.getId());
        assertThat(loanDemand1).isEqualTo(loanDemand2);
        loanDemand2.setId(2L);
        assertThat(loanDemand1).isNotEqualTo(loanDemand2);
        loanDemand1.setId(null);
        assertThat(loanDemand1).isNotEqualTo(loanDemand2);
    }
}
