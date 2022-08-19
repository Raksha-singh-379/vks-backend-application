package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.AmortizationDetails;
import com.vgtech.vks.app.domain.LoanDetails;
import com.vgtech.vks.app.service.dto.AmortizationDetailsDTO;
import com.vgtech.vks.app.service.dto.LoanDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AmortizationDetails} and its DTO {@link AmortizationDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AmortizationDetailsMapper extends EntityMapper<AmortizationDetailsDTO, AmortizationDetails> {
    @Mapping(target = "loanDetails", source = "loanDetails", qualifiedByName = "loanDetailsId")
    AmortizationDetailsDTO toDto(AmortizationDetails s);

    @Named("loanDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LoanDetailsDTO toDtoLoanDetailsId(LoanDetails loanDetails);
}
