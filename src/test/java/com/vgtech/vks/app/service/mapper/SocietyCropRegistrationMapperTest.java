package com.vgtech.vks.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SocietyCropRegistrationMapperTest {

    private SocietyCropRegistrationMapper societyCropRegistrationMapper;

    @BeforeEach
    public void setUp() {
        societyCropRegistrationMapper = new SocietyCropRegistrationMapperImpl();
    }
}
