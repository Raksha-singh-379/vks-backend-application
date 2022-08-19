package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyAssetsDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyAssetsDataDTO.class);
        SocietyAssetsDataDTO societyAssetsDataDTO1 = new SocietyAssetsDataDTO();
        societyAssetsDataDTO1.setId(1L);
        SocietyAssetsDataDTO societyAssetsDataDTO2 = new SocietyAssetsDataDTO();
        assertThat(societyAssetsDataDTO1).isNotEqualTo(societyAssetsDataDTO2);
        societyAssetsDataDTO2.setId(societyAssetsDataDTO1.getId());
        assertThat(societyAssetsDataDTO1).isEqualTo(societyAssetsDataDTO2);
        societyAssetsDataDTO2.setId(2L);
        assertThat(societyAssetsDataDTO1).isNotEqualTo(societyAssetsDataDTO2);
        societyAssetsDataDTO1.setId(null);
        assertThat(societyAssetsDataDTO1).isNotEqualTo(societyAssetsDataDTO2);
    }
}
