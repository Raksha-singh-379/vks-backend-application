package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccountMappingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountMappingDTO.class);
        AccountMappingDTO accountMappingDTO1 = new AccountMappingDTO();
        accountMappingDTO1.setId(1L);
        AccountMappingDTO accountMappingDTO2 = new AccountMappingDTO();
        assertThat(accountMappingDTO1).isNotEqualTo(accountMappingDTO2);
        accountMappingDTO2.setId(accountMappingDTO1.getId());
        assertThat(accountMappingDTO1).isEqualTo(accountMappingDTO2);
        accountMappingDTO2.setId(2L);
        assertThat(accountMappingDTO1).isNotEqualTo(accountMappingDTO2);
        accountMappingDTO1.setId(null);
        assertThat(accountMappingDTO1).isNotEqualTo(accountMappingDTO2);
    }
}
