package com.vgtech.vks.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GRInterestDetailsMapperTest {

    private GRInterestDetailsMapper gRInterestDetailsMapper;

    @BeforeEach
    public void setUp() {
        gRInterestDetailsMapper = new GRInterestDetailsMapperImpl();
    }
}
