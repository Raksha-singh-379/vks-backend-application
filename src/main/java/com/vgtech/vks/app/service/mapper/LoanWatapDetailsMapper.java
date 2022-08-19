package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.LoanDemand;
import com.vgtech.vks.app.domain.LoanWatapDetails;
import com.vgtech.vks.app.service.dto.LoanDemandDTO;
import com.vgtech.vks.app.service.dto.LoanWatapDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoanWatapDetails} and its DTO {@link LoanWatapDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoanWatapDetailsMapper extends EntityMapper<LoanWatapDetailsDTO, LoanWatapDetails> {
    @Mapping(target = "loanDemand", source = "loanDemand", qualifiedByName = "loanDemandId")
    LoanWatapDetailsDTO toDto(LoanWatapDetails s);

    @Named("loanDemandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LoanDemandDTO toDtoLoanDemandId(LoanDemand loanDemand);
}
