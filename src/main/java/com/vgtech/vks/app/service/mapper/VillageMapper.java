package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.Village;
import com.vgtech.vks.app.service.dto.VillageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Village} and its DTO {@link VillageDTO}.
 */
@Mapper(componentModel = "spring")
public interface VillageMapper extends EntityMapper<VillageDTO, Village> {}
