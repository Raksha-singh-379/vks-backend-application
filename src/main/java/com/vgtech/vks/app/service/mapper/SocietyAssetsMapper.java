package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyAssets;
import com.vgtech.vks.app.service.dto.SocietyAssetsDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SocietyAssets} and its DTO {@link SocietyAssetsDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocietyAssetsMapper extends EntityMapper<SocietyAssetsDTO, SocietyAssets> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    SocietyAssetsDTO toDto(SocietyAssets s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}
