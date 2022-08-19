package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyConfig.class);
        SocietyConfig societyConfig1 = new SocietyConfig();
        societyConfig1.setId(1L);
        SocietyConfig societyConfig2 = new SocietyConfig();
        societyConfig2.setId(societyConfig1.getId());
        assertThat(societyConfig1).isEqualTo(societyConfig2);
        societyConfig2.setId(2L);
        assertThat(societyConfig1).isNotEqualTo(societyConfig2);
        societyConfig1.setId(null);
        assertThat(societyConfig1).isNotEqualTo(societyConfig2);
    }
}
