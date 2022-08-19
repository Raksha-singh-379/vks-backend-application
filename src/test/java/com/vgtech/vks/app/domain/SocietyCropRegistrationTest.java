package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyCropRegistrationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyCropRegistration.class);
        SocietyCropRegistration societyCropRegistration1 = new SocietyCropRegistration();
        societyCropRegistration1.setId(1L);
        SocietyCropRegistration societyCropRegistration2 = new SocietyCropRegistration();
        societyCropRegistration2.setId(societyCropRegistration1.getId());
        assertThat(societyCropRegistration1).isEqualTo(societyCropRegistration2);
        societyCropRegistration2.setId(2L);
        assertThat(societyCropRegistration1).isNotEqualTo(societyCropRegistration2);
        societyCropRegistration1.setId(null);
        assertThat(societyCropRegistration1).isNotEqualTo(societyCropRegistration2);
    }
}
