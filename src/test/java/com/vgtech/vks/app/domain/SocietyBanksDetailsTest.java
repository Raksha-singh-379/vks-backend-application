package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyBanksDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyBanksDetails.class);
        SocietyBanksDetails societyBanksDetails1 = new SocietyBanksDetails();
        societyBanksDetails1.setId(1L);
        SocietyBanksDetails societyBanksDetails2 = new SocietyBanksDetails();
        societyBanksDetails2.setId(societyBanksDetails1.getId());
        assertThat(societyBanksDetails1).isEqualTo(societyBanksDetails2);
        societyBanksDetails2.setId(2L);
        assertThat(societyBanksDetails1).isNotEqualTo(societyBanksDetails2);
        societyBanksDetails1.setId(null);
        assertThat(societyBanksDetails1).isNotEqualTo(societyBanksDetails2);
    }
}
