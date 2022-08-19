package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyConfigDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyConfigDTO.class);
        SocietyConfigDTO societyConfigDTO1 = new SocietyConfigDTO();
        societyConfigDTO1.setId(1L);
        SocietyConfigDTO societyConfigDTO2 = new SocietyConfigDTO();
        assertThat(societyConfigDTO1).isNotEqualTo(societyConfigDTO2);
        societyConfigDTO2.setId(societyConfigDTO1.getId());
        assertThat(societyConfigDTO1).isEqualTo(societyConfigDTO2);
        societyConfigDTO2.setId(2L);
        assertThat(societyConfigDTO1).isNotEqualTo(societyConfigDTO2);
        societyConfigDTO1.setId(null);
        assertThat(societyConfigDTO1).isNotEqualTo(societyConfigDTO2);
    }
}
