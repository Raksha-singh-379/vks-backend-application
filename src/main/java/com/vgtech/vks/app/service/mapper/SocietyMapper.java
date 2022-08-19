package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.District;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.State;
import com.vgtech.vks.app.domain.Taluka;
import com.vgtech.vks.app.domain.Village;
import com.vgtech.vks.app.service.dto.DistrictDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import com.vgtech.vks.app.service.dto.StateDTO;
import com.vgtech.vks.app.service.dto.TalukaDTO;
import com.vgtech.vks.app.service.dto.VillageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Society} and its DTO {@link SocietyDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocietyMapper extends EntityMapper<SocietyDTO, Society> {
    @Mapping(target = "city", source = "city", qualifiedByName = "villageId")
    @Mapping(target = "state", source = "state", qualifiedByName = "stateId")
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    @Mapping(target = "taluka", source = "taluka", qualifiedByName = "talukaId")
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    SocietyDTO toDto(Society s);

    @Named("villageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VillageDTO toDtoVillageId(Village village);

    @Named("stateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StateDTO toDtoStateId(State state);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);

    @Named("talukaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TalukaDTO toDtoTalukaId(Taluka taluka);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}
