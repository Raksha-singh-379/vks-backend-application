package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyNpaSettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyNpaSetting.class);
        SocietyNpaSetting societyNpaSetting1 = new SocietyNpaSetting();
        societyNpaSetting1.setId(1L);
        SocietyNpaSetting societyNpaSetting2 = new SocietyNpaSetting();
        societyNpaSetting2.setId(societyNpaSetting1.getId());
        assertThat(societyNpaSetting1).isEqualTo(societyNpaSetting2);
        societyNpaSetting2.setId(2L);
        assertThat(societyNpaSetting1).isNotEqualTo(societyNpaSetting2);
        societyNpaSetting1.setId(null);
        assertThat(societyNpaSetting1).isNotEqualTo(societyNpaSetting2);
    }
}
