package com.vgtech.vks.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExpenditureTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpenditureTypeDTO.class);
        ExpenditureTypeDTO expenditureTypeDTO1 = new ExpenditureTypeDTO();
        expenditureTypeDTO1.setId(1L);
        ExpenditureTypeDTO expenditureTypeDTO2 = new ExpenditureTypeDTO();
        assertThat(expenditureTypeDTO1).isNotEqualTo(expenditureTypeDTO2);
        expenditureTypeDTO2.setId(expenditureTypeDTO1.getId());
        assertThat(expenditureTypeDTO1).isEqualTo(expenditureTypeDTO2);
        expenditureTypeDTO2.setId(2L);
        assertThat(expenditureTypeDTO1).isNotEqualTo(expenditureTypeDTO2);
        expenditureTypeDTO1.setId(null);
        assertThat(expenditureTypeDTO1).isNotEqualTo(expenditureTypeDTO2);
    }
}
