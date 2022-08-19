package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.BankDhoranDetails;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.service.dto.BankDhoranDetailsDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankDhoranDetails} and its DTO {@link BankDhoranDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankDhoranDetailsMapper extends EntityMapper<BankDhoranDetailsDTO, BankDhoranDetails> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    BankDhoranDetailsDTO toDto(BankDhoranDetails s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}
