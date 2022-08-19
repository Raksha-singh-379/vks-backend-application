package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankDhoranDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankDhoranDetailsDTO.class);
        BankDhoranDetailsDTO bankDhoranDetailsDTO1 = new BankDhoranDetailsDTO();
        bankDhoranDetailsDTO1.setId(1L);
        BankDhoranDetailsDTO bankDhoranDetailsDTO2 = new BankDhoranDetailsDTO();
        assertThat(bankDhoranDetailsDTO1).isNotEqualTo(bankDhoranDetailsDTO2);
        bankDhoranDetailsDTO2.setId(bankDhoranDetailsDTO1.getId());
        assertThat(bankDhoranDetailsDTO1).isEqualTo(bankDhoranDetailsDTO2);
        bankDhoranDetailsDTO2.setId(2L);
        assertThat(bankDhoranDetailsDTO1).isNotEqualTo(bankDhoranDetailsDTO2);
        bankDhoranDetailsDTO1.setId(null);
        assertThat(bankDhoranDetailsDTO1).isNotEqualTo(bankDhoranDetailsDTO2);
    }
}
