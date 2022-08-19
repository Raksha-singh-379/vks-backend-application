package com.vgtech.vks.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgtech.vks.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankDhoranDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankDhoranDetails.class);
        BankDhoranDetails bankDhoranDetails1 = new BankDhoranDetails();
        bankDhoranDetails1.setId(1L);
        BankDhoranDetails bankDhoranDetails2 = new BankDhoranDetails();
        bankDhoranDetails2.setId(bankDhoranDetails1.getId());
        assertThat(bankDhoranDetails1).isEqualTo(bankDhoranDetails2);
        bankDhoranDetails2.setId(2L);
        assertThat(bankDhoranDetails1).isNotEqualTo(bankDhoranDetails2);
        bankDhoranDetails1.setId(null);
        assertThat(bankDhoranDetails1).isNotEqualTo(bankDhoranDetails2);
    }
}
