package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.SchemesDetails;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.service.dto.SchemesDetailsDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SchemesDetails} and its DTO {@link SchemesDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface SchemesDetailsMapper extends EntityMapper<SchemesDetailsDTO, SchemesDetails> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    SchemesDetailsDTO toDto(SchemesDetails s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}
