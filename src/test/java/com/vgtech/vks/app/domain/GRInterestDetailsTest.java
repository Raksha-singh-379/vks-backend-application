package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GRInterestDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GRInterestDetails.class);
        GRInterestDetails gRInterestDetails1 = new GRInterestDetails();
        gRInterestDetails1.setId(1L);
        GRInterestDetails gRInterestDetails2 = new GRInterestDetails();
        gRInterestDetails2.setId(gRInterestDetails1.getId());
        assertThat(gRInterestDetails1).isEqualTo(gRInterestDetails2);
        gRInterestDetails2.setId(2L);
        assertThat(gRInterestDetails1).isNotEqualTo(gRInterestDetails2);
        gRInterestDetails1.setId(null);
        assertThat(gRInterestDetails1).isNotEqualTo(gRInterestDetails2);
    }
}
