package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AmortizationDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationDetails.class);
        AmortizationDetails amortizationDetails1 = new AmortizationDetails();
        amortizationDetails1.setId(1L);
        AmortizationDetails amortizationDetails2 = new AmortizationDetails();
        amortizationDetails2.setId(amortizationDetails1.getId());
        assertThat(amortizationDetails1).isEqualTo(amortizationDetails2);
        amortizationDetails2.setId(2L);
        assertThat(amortizationDetails1).isNotEqualTo(amortizationDetails2);
        amortizationDetails1.setId(null);
        assertThat(amortizationDetails1).isNotEqualTo(amortizationDetails2);
    }
}
