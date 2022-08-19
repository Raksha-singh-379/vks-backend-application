package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanDemandDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanDemandDTO.class);
        LoanDemandDTO loanDemandDTO1 = new LoanDemandDTO();
        loanDemandDTO1.setId(1L);
        LoanDemandDTO loanDemandDTO2 = new LoanDemandDTO();
        assertThat(loanDemandDTO1).isNotEqualTo(loanDemandDTO2);
        loanDemandDTO2.setId(loanDemandDTO1.getId());
        assertThat(loanDemandDTO1).isEqualTo(loanDemandDTO2);
        loanDemandDTO2.setId(2L);
        assertThat(loanDemandDTO1).isNotEqualTo(loanDemandDTO2);
        loanDemandDTO1.setId(null);
        assertThat(loanDemandDTO1).isNotEqualTo(loanDemandDTO2);
    }
}
