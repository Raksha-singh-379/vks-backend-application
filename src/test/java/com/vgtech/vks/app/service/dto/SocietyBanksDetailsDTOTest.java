package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyBanksDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyBanksDetailsDTO.class);
        SocietyBanksDetailsDTO societyBanksDetailsDTO1 = new SocietyBanksDetailsDTO();
        societyBanksDetailsDTO1.setId(1L);
        SocietyBanksDetailsDTO societyBanksDetailsDTO2 = new SocietyBanksDetailsDTO();
        assertThat(societyBanksDetailsDTO1).isNotEqualTo(societyBanksDetailsDTO2);
        societyBanksDetailsDTO2.setId(societyBanksDetailsDTO1.getId());
        assertThat(societyBanksDetailsDTO1).isEqualTo(societyBanksDetailsDTO2);
        societyBanksDetailsDTO2.setId(2L);
        assertThat(societyBanksDetailsDTO1).isNotEqualTo(societyBanksDetailsDTO2);
        societyBanksDetailsDTO1.setId(null);
        assertThat(societyBanksDetailsDTO1).isNotEqualTo(societyBanksDetailsDTO2);
    }
}
