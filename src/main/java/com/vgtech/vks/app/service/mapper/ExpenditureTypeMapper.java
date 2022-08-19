package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.ExpenditureType;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.service.dto.ExpenditureTypeDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExpenditureType} and its DTO {@link ExpenditureTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExpenditureTypeMapper extends EntityMapper<ExpenditureTypeDTO, ExpenditureType> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    ExpenditureTypeDTO toDto(ExpenditureType s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}
