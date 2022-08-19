package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.BankDhoranDetails;
import com.vgtech.vks.app.domain.GRInterestDetails;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyLoanProduct;
import com.vgtech.vks.app.service.dto.BankDhoranDetailsDTO;
import com.vgtech.vks.app.service.dto.GRInterestDetailsDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import com.vgtech.vks.app.service.dto.SocietyLoanProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SocietyLoanProduct} and its DTO {@link SocietyLoanProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocietyLoanProductMapper extends EntityMapper<SocietyLoanProductDTO, SocietyLoanProduct> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    @Mapping(target = "bankDhoranDetails", source = "bankDhoranDetails", qualifiedByName = "bankDhoranDetailsId")
    @Mapping(target = "gRInterestDetails", source = "gRInterestDetails", qualifiedByName = "gRInterestDetailsId")
    SocietyLoanProductDTO toDto(SocietyLoanProduct s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);

    @Named("bankDhoranDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankDhoranDetailsDTO toDtoBankDhoranDetailsId(BankDhoranDetails bankDhoranDetails);

    @Named("gRInterestDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GRInterestDetailsDTO toDtoGRInterestDetailsId(GRInterestDetails gRInterestDetails);
}
