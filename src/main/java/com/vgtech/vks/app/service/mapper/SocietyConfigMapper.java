package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.BankDhoranDetails;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyConfig;
import com.vgtech.vks.app.service.dto.BankDhoranDetailsDTO;
import com.vgtech.vks.app.service.dto.SocietyConfigDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SocietyConfig} and its DTO {@link SocietyConfigDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocietyConfigMapper extends EntityMapper<SocietyConfigDTO, SocietyConfig> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    @Mapping(target = "bankDhoranDetails", source = "bankDhoranDetails", qualifiedByName = "bankDhoranDetailsId")
    SocietyConfigDTO toDto(SocietyConfig s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);

    @Named("bankDhoranDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankDhoranDetailsDTO toDtoBankDhoranDetailsId(BankDhoranDetails bankDhoranDetails);
}
