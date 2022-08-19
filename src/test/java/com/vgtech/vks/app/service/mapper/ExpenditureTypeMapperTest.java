package com.vgtech.vks.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpenditureTypeMapperTest {

    private ExpenditureTypeMapper expenditureTypeMapper;

    @BeforeEach
    public void setUp() {
        expenditureTypeMapper = new ExpenditureTypeMapperImpl();
    }
}
