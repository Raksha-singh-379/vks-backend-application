package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.AccountMapping;
import com.vgtech.vks.app.domain.LedgerAccounts;
import com.vgtech.vks.app.service.dto.AccountMappingDTO;
import com.vgtech.vks.app.service.dto.LedgerAccountsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountMapping} and its DTO {@link AccountMappingDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccountMappingMapper extends EntityMapper<AccountMappingDTO, AccountMapping> {
    @Mapping(target = "accountMapping", source = "accountMapping", qualifiedByName = "ledgerAccountsId")
    AccountMappingDTO toDto(AccountMapping s);

    @Named("ledgerAccountsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LedgerAccountsDTO toDtoLedgerAccountsId(LedgerAccounts ledgerAccounts);
}
