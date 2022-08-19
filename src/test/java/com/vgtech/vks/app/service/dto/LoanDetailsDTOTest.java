package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanDetailsDTO.class);
        LoanDetailsDTO loanDetailsDTO1 = new LoanDetailsDTO();
        loanDetailsDTO1.setId(1L);
        LoanDetailsDTO loanDetailsDTO2 = new LoanDetailsDTO();
        assertThat(loanDetailsDTO1).isNotEqualTo(loanDetailsDTO2);
        loanDetailsDTO2.setId(loanDetailsDTO1.getId());
        assertThat(loanDetailsDTO1).isEqualTo(loanDetailsDTO2);
        loanDetailsDTO2.setId(2L);
        assertThat(loanDetailsDTO1).isNotEqualTo(loanDetailsDTO2);
        loanDetailsDTO1.setId(null);
        assertThat(loanDetailsDTO1).isNotEqualTo(loanDetailsDTO2);
    }
}
