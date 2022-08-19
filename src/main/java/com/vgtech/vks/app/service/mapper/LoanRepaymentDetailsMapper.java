package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.LoanDetails;
import com.vgtech.vks.app.domain.LoanRepaymentDetails;
import com.vgtech.vks.app.service.dto.LoanDetailsDTO;
import com.vgtech.vks.app.service.dto.LoanRepaymentDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoanRepaymentDetails} and its DTO {@link LoanRepaymentDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoanRepaymentDetailsMapper extends EntityMapper<LoanRepaymentDetailsDTO, LoanRepaymentDetails> {
    @Mapping(target = "loanDetails", source = "loanDetails", qualifiedByName = "loanDetailsId")
    LoanRepaymentDetailsDTO toDto(LoanRepaymentDetails s);

    @Named("loanDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LoanDetailsDTO toDtoLoanDetailsId(LoanDetails loanDetails);
}
