package com.vgtech.vks.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoanDisbursementDetailsMapperTest {

    private LoanDisbursementDetailsMapper loanDisbursementDetailsMapper;

    @BeforeEach
    public void setUp() {
        loanDisbursementDetailsMapper = new LoanDisbursementDetailsMapperImpl();
    }
}
