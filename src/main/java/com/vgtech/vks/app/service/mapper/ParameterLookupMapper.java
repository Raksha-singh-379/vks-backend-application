package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.ParameterLookup;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.service.dto.ParameterLookupDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterLookup} and its DTO {@link ParameterLookupDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParameterLookupMapper extends EntityMapper<ParameterLookupDTO, ParameterLookup> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    ParameterLookupDTO toDto(ParameterLookup s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}
