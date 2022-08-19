package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.SocietyAssets;
import com.vgtech.vks.app.domain.SocietyAssetsData;
import com.vgtech.vks.app.service.dto.SocietyAssetsDTO;
import com.vgtech.vks.app.service.dto.SocietyAssetsDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SocietyAssetsData} and its DTO {@link SocietyAssetsDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocietyAssetsDataMapper extends EntityMapper<SocietyAssetsDataDTO, SocietyAssetsData> {
    @Mapping(target = "societyAssets", source = "societyAssets", qualifiedByName = "societyAssetsId")
    SocietyAssetsDataDTO toDto(SocietyAssetsData s);

    @Named("societyAssetsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyAssetsDTO toDtoSocietyAssetsId(SocietyAssets societyAssets);
}
