package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyPrerequisite;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import com.vgtech.vks.app.service.dto.SocietyPrerequisiteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SocietyPrerequisite} and its DTO {@link SocietyPrerequisiteDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocietyPrerequisiteMapper extends EntityMapper<SocietyPrerequisiteDTO, SocietyPrerequisite> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    SocietyPrerequisiteDTO toDto(SocietyPrerequisite s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}
