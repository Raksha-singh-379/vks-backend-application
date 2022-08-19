package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyPrerequisiteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyPrerequisiteDTO.class);
        SocietyPrerequisiteDTO societyPrerequisiteDTO1 = new SocietyPrerequisiteDTO();
        societyPrerequisiteDTO1.setId(1L);
        SocietyPrerequisiteDTO societyPrerequisiteDTO2 = new SocietyPrerequisiteDTO();
        assertThat(societyPrerequisiteDTO1).isNotEqualTo(societyPrerequisiteDTO2);
        societyPrerequisiteDTO2.setId(societyPrerequisiteDTO1.getId());
        assertThat(societyPrerequisiteDTO1).isEqualTo(societyPrerequisiteDTO2);
        societyPrerequisiteDTO2.setId(2L);
        assertThat(societyPrerequisiteDTO1).isNotEqualTo(societyPrerequisiteDTO2);
        societyPrerequisiteDTO1.setId(null);
        assertThat(societyPrerequisiteDTO1).isNotEqualTo(societyPrerequisiteDTO2);
    }
}
