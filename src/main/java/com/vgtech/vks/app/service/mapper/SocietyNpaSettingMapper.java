package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyNpaSetting;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import com.vgtech.vks.app.service.dto.SocietyNpaSettingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SocietyNpaSetting} and its DTO {@link SocietyNpaSettingDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocietyNpaSettingMapper extends EntityMapper<SocietyNpaSettingDTO, SocietyNpaSetting> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    SocietyNpaSettingDTO toDto(SocietyNpaSetting s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}
