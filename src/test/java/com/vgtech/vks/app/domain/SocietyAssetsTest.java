package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocietyAssetsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocietyAssets.class);
        SocietyAssets societyAssets1 = new SocietyAssets();
        societyAssets1.setId(1L);
        SocietyAssets societyAssets2 = new SocietyAssets();
        societyAssets2.setId(societyAssets1.getId());
        assertThat(societyAssets1).isEqualTo(societyAssets2);
        societyAssets2.setId(2L);
        assertThat(societyAssets1).isNotEqualTo(societyAssets2);
        societyAssets1.setId(null);
        assertThat(societyAssets1).isNotEqualTo(societyAssets2);
    }
}
