package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.LedgerAccounts;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.service.dto.LedgerAccountsDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LedgerAccounts} and its DTO {@link LedgerAccountsDTO}.
 */
@Mapper(componentModel = "spring")
public interface LedgerAccountsMapper extends EntityMapper<LedgerAccountsDTO, LedgerAccounts> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    @Mapping(target = "ledgerAccounts", source = "ledgerAccounts", qualifiedByName = "ledgerAccountsId")
    LedgerAccountsDTO toDto(LedgerAccounts s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);

    @Named("ledgerAccountsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LedgerAccountsDTO toDtoLedgerAccountsId(LedgerAccounts ledgerAccounts);
}
