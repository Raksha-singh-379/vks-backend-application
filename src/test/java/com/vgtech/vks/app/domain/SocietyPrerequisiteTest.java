package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyPrerequisiteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyPrerequisite.class);
        SocietyPrerequisite societyPrerequisite1 = new SocietyPrerequisite();
        societyPrerequisite1.setId(1L);
        SocietyPrerequisite societyPrerequisite2 = new SocietyPrerequisite();
        societyPrerequisite2.setId(societyPrerequisite1.getId());
        assertThat(societyPrerequisite1).isEqualTo(societyPrerequisite2);
        societyPrerequisite2.setId(2L);
        assertThat(societyPrerequisite1).isNotEqualTo(societyPrerequisite2);
        societyPrerequisite1.setId(null);
        assertThat(societyPrerequisite1).isNotEqualTo(societyPrerequisite2);
    }
}
