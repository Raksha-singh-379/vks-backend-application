package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.Member;
import com.vgtech.vks.app.domain.MemberBank;
import com.vgtech.vks.app.service.dto.MemberBankDTO;
import com.vgtech.vks.app.service.dto.MemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MemberBank} and its DTO {@link MemberBankDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberBankMapper extends EntityMapper<MemberBankDTO, MemberBank> {
    @Mapping(target = "member", source = "member", qualifiedByName = "memberId")
    @Mapping(target = "member", source = "member", qualifiedByName = "memberId")
    MemberBankDTO toDto(MemberBank s);

    @Named("memberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberDTO toDtoMemberId(Member member);
}
