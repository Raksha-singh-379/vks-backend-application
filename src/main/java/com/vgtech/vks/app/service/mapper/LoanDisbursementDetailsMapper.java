package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.LoanDetails;
import com.vgtech.vks.app.domain.LoanDisbursementDetails;
import com.vgtech.vks.app.service.dto.LoanDetailsDTO;
import com.vgtech.vks.app.service.dto.LoanDisbursementDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoanDisbursementDetails} and its DTO {@link LoanDisbursementDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoanDisbursementDetailsMapper extends EntityMapper<LoanDisbursementDetailsDTO, LoanDisbursementDetails> {
    @Mapping(target = "loanDetails", source = "loanDetails", qualifiedByName = "loanDetailsId")
    LoanDisbursementDetailsDTO toDto(LoanDisbursementDetails s);

    @Named("loanDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LoanDetailsDTO toDtoLoanDetailsId(LoanDetails loanDetails);
}
