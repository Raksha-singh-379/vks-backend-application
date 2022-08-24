package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.AddressDetails;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.service.dto.AddressDetailsDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Society} and its DTO {@link SocietyDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocietyMapper extends EntityMapper<SocietyDTO, Society> {
    @Mapping(target = "addressDetails", source = "addressDetails", qualifiedByName = "addressDetailsId")
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    SocietyDTO toDto(Society s);

    @Named("addressDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDetailsDTO toDtoAddressDetailsId(AddressDetails addressDetails);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}
