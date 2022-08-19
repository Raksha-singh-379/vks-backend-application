package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyCropRegistrationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyCropRegistrationDTO.class);
        SocietyCropRegistrationDTO societyCropRegistrationDTO1 = new SocietyCropRegistrationDTO();
        societyCropRegistrationDTO1.setId(1L);
        SocietyCropRegistrationDTO societyCropRegistrationDTO2 = new SocietyCropRegistrationDTO();
        assertThat(societyCropRegistrationDTO1).isNotEqualTo(societyCropRegistrationDTO2);
        societyCropRegistrationDTO2.setId(societyCropRegistrationDTO1.getId());
        assertThat(societyCropRegistrationDTO1).isEqualTo(societyCropRegistrationDTO2);
        societyCropRegistrationDTO2.setId(2L);
        assertThat(societyCropRegistrationDTO1).isNotEqualTo(societyCropRegistrationDTO2);
        societyCropRegistrationDTO1.setId(null);
        assertThat(societyCropRegistrationDTO1).isNotEqualTo(societyCropRegistrationDTO2);
    }
}
