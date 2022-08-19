package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.Documents;
import com.vgtech.vks.app.domain.Member;
import com.vgtech.vks.app.service.dto.DocumentsDTO;
import com.vgtech.vks.app.service.dto.MemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Documents} and its DTO {@link DocumentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentsMapper extends EntityMapper<DocumentsDTO, Documents> {
    @Mapping(target = "member", source = "member", qualifiedByName = "memberId")
    DocumentsDTO toDto(Documents s);

    @Named("memberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberDTO toDtoMemberId(Member member);
}
