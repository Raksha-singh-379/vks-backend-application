package com.vgtech.vks.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SocietyAssetsDataMapperTest {

    private SocietyAssetsDataMapper societyAssetsDataMapper;

    @BeforeEach
    public void setUp() {
        societyAssetsDataMapper = new SocietyAssetsDataMapperImpl();
    }
}
