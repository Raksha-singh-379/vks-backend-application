package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyNpaSettingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyNpaSettingDTO.class);
        SocietyNpaSettingDTO societyNpaSettingDTO1 = new SocietyNpaSettingDTO();
        societyNpaSettingDTO1.setId(1L);
        SocietyNpaSettingDTO societyNpaSettingDTO2 = new SocietyNpaSettingDTO();
        assertThat(societyNpaSettingDTO1).isNotEqualTo(societyNpaSettingDTO2);
        societyNpaSettingDTO2.setId(societyNpaSettingDTO1.getId());
        assertThat(societyNpaSettingDTO1).isEqualTo(societyNpaSettingDTO2);
        societyNpaSettingDTO2.setId(2L);
        assertThat(societyNpaSettingDTO1).isNotEqualTo(societyNpaSettingDTO2);
        societyNpaSettingDTO1.setId(null);
        assertThat(societyNpaSettingDTO1).isNotEqualTo(societyNpaSettingDTO2);
    }
}
