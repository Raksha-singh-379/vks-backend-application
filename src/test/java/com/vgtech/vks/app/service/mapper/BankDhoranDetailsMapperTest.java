package com.vgtech.vks.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankDhoranDetailsMapperTest {

    private BankDhoranDetailsMapper bankDhoranDetailsMapper;

    @BeforeEach
    public void setUp() {
        bankDhoranDetailsMapper = new BankDhoranDetailsMapperImpl();
    }
}
