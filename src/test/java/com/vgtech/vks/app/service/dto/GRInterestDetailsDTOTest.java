package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GRInterestDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GRInterestDetailsDTO.class);
        GRInterestDetailsDTO gRInterestDetailsDTO1 = new GRInterestDetailsDTO();
        gRInterestDetailsDTO1.setId(1L);
        GRInterestDetailsDTO gRInterestDetailsDTO2 = new GRInterestDetailsDTO();
        assertThat(gRInterestDetailsDTO1).isNotEqualTo(gRInterestDetailsDTO2);
        gRInterestDetailsDTO2.setId(gRInterestDetailsDTO1.getId());
        assertThat(gRInterestDetailsDTO1).isEqualTo(gRInterestDetailsDTO2);
        gRInterestDetailsDTO2.setId(2L);
        assertThat(gRInterestDetailsDTO1).isNotEqualTo(gRInterestDetailsDTO2);
        gRInterestDetailsDTO1.setId(null);
        assertThat(gRInterestDetailsDTO1).isNotEqualTo(gRInterestDetailsDTO2);
    }
}
