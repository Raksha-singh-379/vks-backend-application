package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccountMappingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountMapping.class);
        AccountMapping accountMapping1 = new AccountMapping();
        accountMapping1.setId(1L);
        AccountMapping accountMapping2 = new AccountMapping();
        accountMapping2.setId(accountMapping1.getId());
        assertThat(accountMapping1).isEqualTo(accountMapping2);
        accountMapping2.setId(2L);
        assertThat(accountMapping1).isNotEqualTo(accountMapping2);
        accountMapping1.setId(null);
        assertThat(accountMapping1).isNotEqualTo(accountMapping2);
    }
}
