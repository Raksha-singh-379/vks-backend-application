package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.LoanDemand;
import com.vgtech.vks.app.domain.Member;
import com.vgtech.vks.app.domain.MemberLandAssets;
import com.vgtech.vks.app.domain.SocietyCropRegistration;
import com.vgtech.vks.app.domain.SocietyLoanProduct;
import com.vgtech.vks.app.service.dto.LoanDemandDTO;
import com.vgtech.vks.app.service.dto.MemberDTO;
import com.vgtech.vks.app.service.dto.MemberLandAssetsDTO;
import com.vgtech.vks.app.service.dto.SocietyCropRegistrationDTO;
import com.vgtech.vks.app.service.dto.SocietyLoanProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoanDemand} and its DTO {@link LoanDemandDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoanDemandMapper extends EntityMapper<LoanDemandDTO, LoanDemand> {
    @Mapping(target = "member", source = "member", qualifiedByName = "memberId")
    @Mapping(target = "societyLoanProduct", source = "societyLoanProduct", qualifiedByName = "societyLoanProductId")
    @Mapping(target = "memberLandAssets", source = "memberLandAssets", qualifiedByName = "memberLandAssetsId")
    @Mapping(target = "societyCropRegistration", source = "societyCropRegistration", qualifiedByName = "societyCropRegistrationId")
    LoanDemandDTO toDto(LoanDemand s);

    @Named("memberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberDTO toDtoMemberId(Member member);

    @Named("societyLoanProductId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyLoanProductDTO toDtoSocietyLoanProductId(SocietyLoanProduct societyLoanProduct);

    @Named("memberLandAssetsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberLandAssetsDTO toDtoMemberLandAssetsId(MemberLandAssets memberLandAssets);

    @Named("societyCropRegistrationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyCropRegistrationDTO toDtoSocietyCropRegistrationId(SocietyCropRegistration societyCropRegistration);
}
