package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyLoanProductDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyLoanProductDTO.class);
        SocietyLoanProductDTO societyLoanProductDTO1 = new SocietyLoanProductDTO();
        societyLoanProductDTO1.setId(1L);
        SocietyLoanProductDTO societyLoanProductDTO2 = new SocietyLoanProductDTO();
        assertThat(societyLoanProductDTO1).isNotEqualTo(societyLoanProductDTO2);
        societyLoanProductDTO2.setId(societyLoanProductDTO1.getId());
        assertThat(societyLoanProductDTO1).isEqualTo(societyLoanProductDTO2);
        societyLoanProductDTO2.setId(2L);
        assertThat(societyLoanProductDTO1).isNotEqualTo(societyLoanProductDTO2);
        societyLoanProductDTO1.setId(null);
        assertThat(societyLoanProductDTO1).isNotEqualTo(societyLoanProductDTO2);
    }
}
