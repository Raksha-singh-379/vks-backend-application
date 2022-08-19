package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanWatapDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanWatapDetailsDTO.class);
        LoanWatapDetailsDTO loanWatapDetailsDTO1 = new LoanWatapDetailsDTO();
        loanWatapDetailsDTO1.setId(1L);
        LoanWatapDetailsDTO loanWatapDetailsDTO2 = new LoanWatapDetailsDTO();
        assertThat(loanWatapDetailsDTO1).isNotEqualTo(loanWatapDetailsDTO2);
        loanWatapDetailsDTO2.setId(loanWatapDetailsDTO1.getId());
        assertThat(loanWatapDetailsDTO1).isEqualTo(loanWatapDetailsDTO2);
        loanWatapDetailsDTO2.setId(2L);
        assertThat(loanWatapDetailsDTO1).isNotEqualTo(loanWatapDetailsDTO2);
        loanWatapDetailsDTO1.setId(null);
        assertThat(loanWatapDetailsDTO1).isNotEqualTo(loanWatapDetailsDTO2);
    }
}
