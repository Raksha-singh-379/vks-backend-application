package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.GRInterestDetails;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.service.dto.GRInterestDetailsDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GRInterestDetails} and its DTO {@link GRInterestDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface GRInterestDetailsMapper extends EntityMapper<GRInterestDetailsDTO, GRInterestDetails> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    GRInterestDetailsDTO toDto(GRInterestDetails s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}
