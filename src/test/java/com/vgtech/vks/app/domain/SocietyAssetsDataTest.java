package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyAssetsDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyAssetsData.class);
        SocietyAssetsData societyAssetsData1 = new SocietyAssetsData();
        societyAssetsData1.setId(1L);
        SocietyAssetsData societyAssetsData2 = new SocietyAssetsData();
        societyAssetsData2.setId(societyAssetsData1.getId());
        assertThat(societyAssetsData1).isEqualTo(societyAssetsData2);
        societyAssetsData2.setId(2L);
        assertThat(societyAssetsData1).isNotEqualTo(societyAssetsData2);
        societyAssetsData1.setId(null);
        assertThat(societyAssetsData1).isNotEqualTo(societyAssetsData2);
    }
}
