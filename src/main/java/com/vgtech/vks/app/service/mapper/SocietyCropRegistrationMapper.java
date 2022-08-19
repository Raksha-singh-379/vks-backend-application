package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyCropRegistration;
import com.vgtech.vks.app.service.dto.SocietyCropRegistrationDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SocietyCropRegistration} and its DTO {@link SocietyCropRegistrationDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocietyCropRegistrationMapper extends EntityMapper<SocietyCropRegistrationDTO, SocietyCropRegistration> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    SocietyCropRegistrationDTO toDto(SocietyCropRegistration s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}
