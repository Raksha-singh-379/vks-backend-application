package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExpenditureTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpenditureType.class);
        ExpenditureType expenditureType1 = new ExpenditureType();
        expenditureType1.setId(1L);
        ExpenditureType expenditureType2 = new ExpenditureType();
        expenditureType2.setId(expenditureType1.getId());
        assertThat(expenditureType1).isEqualTo(expenditureType2);
        expenditureType2.setId(2L);
        assertThat(expenditureType1).isNotEqualTo(expenditureType2);
        expenditureType1.setId(null);
        assertThat(expenditureType1).isNotEqualTo(expenditureType2);
    }
}
