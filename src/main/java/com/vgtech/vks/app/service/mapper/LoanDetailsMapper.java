package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.BankDhoranDetails;
import com.vgtech.vks.app.domain.LoanDemand;
import com.vgtech.vks.app.domain.LoanDetails;
import com.vgtech.vks.app.domain.Member;
import com.vgtech.vks.app.domain.SocietyLoanProduct;
import com.vgtech.vks.app.service.dto.BankDhoranDetailsDTO;
import com.vgtech.vks.app.service.dto.LoanDemandDTO;
import com.vgtech.vks.app.service.dto.LoanDetailsDTO;
import com.vgtech.vks.app.service.dto.MemberDTO;
import com.vgtech.vks.app.service.dto.SocietyLoanProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoanDetails} and its DTO {@link LoanDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoanDetailsMapper extends EntityMapper<LoanDetailsDTO, LoanDetails> {
    @Mapping(target = "loanDemand", source = "loanDemand", qualifiedByName = "loanDemandId")
    @Mapping(target = "member", source = "member", qualifiedByName = "memberId")
    @Mapping(target = "loanDemand", source = "loanDemand", qualifiedByName = "loanDemandId")
    @Mapping(target = "societyLoanProduct", source = "societyLoanProduct", qualifiedByName = "societyLoanProductId")
    @Mapping(target = "bankDhoranDetails", source = "bankDhoranDetails", qualifiedByName = "bankDhoranDetailsId")
    LoanDetailsDTO toDto(LoanDetails s);

    @Named("loanDemandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LoanDemandDTO toDtoLoanDemandId(LoanDemand loanDemand);

    @Named("memberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberDTO toDtoMemberId(Member member);

    @Named("societyLoanProductId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyLoanProductDTO toDtoSocietyLoanProductId(SocietyLoanProduct societyLoanProduct);

    @Named("bankDhoranDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankDhoranDetailsDTO toDtoBankDhoranDetailsId(BankDhoranDetails bankDhoranDetails);
}
