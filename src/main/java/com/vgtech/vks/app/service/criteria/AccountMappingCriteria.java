package com.vgtech.vks.app.service.criteria;

import com.vgtech.vks.app.domain.enumeration.MappingType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.vgtech.vks.app.domain.AccountMapping} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.AccountMappingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /account-mappings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class AccountMappingCriteria implements Serializable, Criteria {

    /**
     * Class for filtering MappingType
     */
    public static class MappingTypeFilter extends Filter<MappingType> {

        public MappingTypeFilter() {}

        public MappingTypeFilter(MappingTypeFilter filter) {
            super(filter);
        }

        @Override
        public MappingTypeFilter copy() {
            return new MappingTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private MappingTypeFilter type;

    private StringFilter mappingName;

    private StringFilter ledgerAccHeadCode;

    private LongFilter ledgerAccountId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter isDeleted;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private LongFilter accountMappingId;

    private Boolean distinct;

    public AccountMappingCriteria() {}

    public AccountMappingCriteria(AccountMappingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.mappingName = other.mappingName == null ? null : other.mappingName.copy();
        this.ledgerAccHeadCode = other.ledgerAccHeadCode == null ? null : other.ledgerAccHeadCode.copy();
        this.ledgerAccountId = other.ledgerAccountId == null ? null : other.ledgerAccountId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.accountMappingId = other.accountMappingId == null ? null : other.accountMappingId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AccountMappingCriteria copy() {
        return new AccountMappingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public MappingTypeFilter getType() {
        return type;
    }

    public MappingTypeFilter type() {
        if (type == null) {
            type = new MappingTypeFilter();
        }
        return type;
    }

    public void setType(MappingTypeFilter type) {
        this.type = type;
    }

    public StringFilter getMappingName() {
        return mappingName;
    }

    public StringFilter mappingName() {
        if (mappingName == null) {
            mappingName = new StringFilter();
        }
        return mappingName;
    }

    public void setMappingName(StringFilter mappingName) {
        this.mappingName = mappingName;
    }

    public StringFilter getLedgerAccHeadCode() {
        return ledgerAccHeadCode;
    }

    public StringFilter ledgerAccHeadCode() {
        if (ledgerAccHeadCode == null) {
            ledgerAccHeadCode = new StringFilter();
        }
        return ledgerAccHeadCode;
    }

    public void setLedgerAccHeadCode(StringFilter ledgerAccHeadCode) {
        this.ledgerAccHeadCode = ledgerAccHeadCode;
    }

    public LongFilter getLedgerAccountId() {
        return ledgerAccountId;
    }

    public LongFilter ledgerAccountId() {
        if (ledgerAccountId == null) {
            ledgerAccountId = new LongFilter();
        }
        return ledgerAccountId;
    }

    public void setLedgerAccountId(LongFilter ledgerAccountId) {
        this.ledgerAccountId = ledgerAccountId;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public InstantFilter createdOn() {
        if (createdOn == null) {
            createdOn = new InstantFilter();
        }
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public BooleanFilter getIsDeleted() {
        return isDeleted;
    }

    public BooleanFilter isDeleted() {
        if (isDeleted == null) {
            isDeleted = new BooleanFilter();
        }
        return isDeleted;
    }

    public void setIsDeleted(BooleanFilter isDeleted) {
        this.isDeleted = isDeleted;
    }

    public StringFilter getFreeField1() {
        return freeField1;
    }

    public StringFilter freeField1() {
        if (freeField1 == null) {
            freeField1 = new StringFilter();
        }
        return freeField1;
    }

    public void setFreeField1(StringFilter freeField1) {
        this.freeField1 = freeField1;
    }

    public StringFilter getFreeField2() {
        return freeField2;
    }

    public StringFilter freeField2() {
        if (freeField2 == null) {
            freeField2 = new StringFilter();
        }
        return freeField2;
    }

    public void setFreeField2(StringFilter freeField2) {
        this.freeField2 = freeField2;
    }

    public StringFilter getFreeField3() {
        return freeField3;
    }

    public StringFilter freeField3() {
        if (freeField3 == null) {
            freeField3 = new StringFilter();
        }
        return freeField3;
    }

    public void setFreeField3(StringFilter freeField3) {
        this.freeField3 = freeField3;
    }

    public LongFilter getAccountMappingId() {
        return accountMappingId;
    }

    public LongFilter accountMappingId() {
        if (accountMappingId == null) {
            accountMappingId = new LongFilter();
        }
        return accountMappingId;
    }

    public void setAccountMappingId(LongFilter accountMappingId) {
        this.accountMappingId = accountMappingId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AccountMappingCriteria that = (AccountMappingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(mappingName, that.mappingName) &&
            Objects.equals(ledgerAccHeadCode, that.ledgerAccHeadCode) &&
            Objects.equals(ledgerAccountId, that.ledgerAccountId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(accountMappingId, that.accountMappingId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            type,
            mappingName,
            ledgerAccHeadCode,
            ledgerAccountId,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            isDeleted,
            freeField1,
            freeField2,
            freeField3,
            accountMappingId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountMappingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (mappingName != null ? "mappingName=" + mappingName + ", " : "") +
            (ledgerAccHeadCode != null ? "ledgerAccHeadCode=" + ledgerAccHeadCode + ", " : "") +
            (ledgerAccountId != null ? "ledgerAccountId=" + ledgerAccountId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (accountMappingId != null ? "accountMappingId=" + accountMappingId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
