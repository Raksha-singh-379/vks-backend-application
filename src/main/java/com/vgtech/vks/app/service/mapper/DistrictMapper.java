package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.District;
import com.vgtech.vks.app.service.dto.DistrictDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {}
