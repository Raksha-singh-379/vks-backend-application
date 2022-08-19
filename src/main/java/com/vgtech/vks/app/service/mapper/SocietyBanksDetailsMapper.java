package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyBanksDetails;
import com.vgtech.vks.app.service.dto.SocietyBanksDetailsDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SocietyBanksDetails} and its DTO {@link SocietyBanksDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocietyBanksDetailsMapper extends EntityMapper<SocietyBanksDetailsDTO, SocietyBanksDetails> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    SocietyBanksDetailsDTO toDto(SocietyBanksDetails s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}
