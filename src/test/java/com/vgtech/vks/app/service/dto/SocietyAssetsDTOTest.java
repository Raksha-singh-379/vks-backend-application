package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyAssetsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyAssetsDTO.class);
        SocietyAssetsDTO societyAssetsDTO1 = new SocietyAssetsDTO();
        societyAssetsDTO1.setId(1L);
        SocietyAssetsDTO societyAssetsDTO2 = new SocietyAssetsDTO();
        assertThat(societyAssetsDTO1).isNotEqualTo(societyAssetsDTO2);
        societyAssetsDTO2.setId(societyAssetsDTO1.getId());
        assertThat(societyAssetsDTO1).isEqualTo(societyAssetsDTO2);
        societyAssetsDTO2.setId(2L);
        assertThat(societyAssetsDTO1).isNotEqualTo(societyAssetsDTO2);
        societyAssetsDTO1.setId(null);
        assertThat(societyAssetsDTO1).isNotEqualTo(societyAssetsDTO2);
    }
}
