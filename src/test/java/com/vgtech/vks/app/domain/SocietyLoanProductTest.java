package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyLoanProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyLoanProduct.class);
        SocietyLoanProduct societyLoanProduct1 = new SocietyLoanProduct();
        societyLoanProduct1.setId(1L);
        SocietyLoanProduct societyLoanProduct2 = new SocietyLoanProduct();
        societyLoanProduct2.setId(societyLoanProduct1.getId());
        assertThat(societyLoanProduct1).isEqualTo(societyLoanProduct2);
        societyLoanProduct2.setId(2L);
        assertThat(societyLoanProduct1).isNotEqualTo(societyLoanProduct2);
        societyLoanProduct1.setId(null);
        assertThat(societyLoanProduct1).isNotEqualTo(societyLoanProduct2);
    }
}
