package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanDisbursementDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanDisbursementDetailsDTO.class);
        LoanDisbursementDetailsDTO loanDisbursementDetailsDTO1 = new LoanDisbursementDetailsDTO();
        loanDisbursementDetailsDTO1.setId(1L);
        LoanDisbursementDetailsDTO loanDisbursementDetailsDTO2 = new LoanDisbursementDetailsDTO();
        assertThat(loanDisbursementDetailsDTO1).isNotEqualTo(loanDisbursementDetailsDTO2);
        loanDisbursementDetailsDTO2.setId(loanDisbursementDetailsDTO1.getId());
        assertThat(loanDisbursementDetailsDTO1).isEqualTo(loanDisbursementDetailsDTO2);
        loanDisbursementDetailsDTO2.setId(2L);
        assertThat(loanDisbursementDetailsDTO1).isNotEqualTo(loanDisbursementDetailsDTO2);
        loanDisbursementDetailsDTO1.setId(null);
        assertThat(loanDisbursementDetailsDTO1).isNotEqualTo(loanDisbursementDetailsDTO2);
    }
}
