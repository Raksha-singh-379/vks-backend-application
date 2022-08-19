package com.vgtech.vks.app.service.criteria;

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
 * Criteria class for the {@link com.vgtech.vks.app.domain.SocietyBanksDetails} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.SocietyBanksDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /society-banks-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SocietyBanksDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter bankName;

    private StringFilter ifsccode;

    private StringFilter branchName;

    private StringFilter accountNumber;

    private BooleanFilter isActive;

    private StringFilter accountType;

    private StringFilter accHeadCode;

    private StringFilter parentAccHeadCode;

    private StringFilter accountName;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private LongFilter societyId;

    private Boolean distinct;

    public SocietyBanksDetailsCriteria() {}

    public SocietyBanksDetailsCriteria(SocietyBanksDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.ifsccode = other.ifsccode == null ? null : other.ifsccode.copy();
        this.branchName = other.branchName == null ? null : other.branchName.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.accountType = other.accountType == null ? null : other.accountType.copy();
        this.accHeadCode = other.accHeadCode == null ? null : other.accHeadCode.copy();
        this.parentAccHeadCode = other.parentAccHeadCode == null ? null : other.parentAccHeadCode.copy();
        this.accountName = other.accountName == null ? null : other.accountName.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.societyId = other.societyId == null ? null : other.societyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SocietyBanksDetailsCriteria copy() {
        return new SocietyBanksDetailsCriteria(this);
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

    public StringFilter getBankName() {
        return bankName;
    }

    public StringFilter bankName() {
        if (bankName == null) {
            bankName = new StringFilter();
        }
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public StringFilter getIfsccode() {
        return ifsccode;
    }

    public StringFilter ifsccode() {
        if (ifsccode == null) {
            ifsccode = new StringFilter();
        }
        return ifsccode;
    }

    public void setIfsccode(StringFilter ifsccode) {
        this.ifsccode = ifsccode;
    }

    public StringFilter getBranchName() {
        return branchName;
    }

    public StringFilter branchName() {
        if (branchName == null) {
            branchName = new StringFilter();
        }
        return branchName;
    }

    public void setBranchName(StringFilter branchName) {
        this.branchName = branchName;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public StringFilter accountNumber() {
        if (accountNumber == null) {
            accountNumber = new StringFilter();
        }
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            isActive = new BooleanFilter();
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public StringFilter getAccountType() {
        return accountType;
    }

    public StringFilter accountType() {
        if (accountType == null) {
            accountType = new StringFilter();
        }
        return accountType;
    }

    public void setAccountType(StringFilter accountType) {
        this.accountType = accountType;
    }

    public StringFilter getAccHeadCode() {
        return accHeadCode;
    }

    public StringFilter accHeadCode() {
        if (accHeadCode == null) {
            accHeadCode = new StringFilter();
        }
        return accHeadCode;
    }

    public void setAccHeadCode(StringFilter accHeadCode) {
        this.accHeadCode = accHeadCode;
    }

    public StringFilter getParentAccHeadCode() {
        return parentAccHeadCode;
    }

    public StringFilter parentAccHeadCode() {
        if (parentAccHeadCode == null) {
            parentAccHeadCode = new StringFilter();
        }
        return parentAccHeadCode;
    }

    public void setParentAccHeadCode(StringFilter parentAccHeadCode) {
        this.parentAccHeadCode = parentAccHeadCode;
    }

    public StringFilter getAccountName() {
        return accountName;
    }

    public StringFilter accountName() {
        if (accountName == null) {
            accountName = new StringFilter();
        }
        return accountName;
    }

    public void setAccountName(StringFilter accountName) {
        this.accountName = accountName;
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

    public LongFilter getSocietyId() {
        return societyId;
    }

    public LongFilter societyId() {
        if (societyId == null) {
            societyId = new LongFilter();
        }
        return societyId;
    }

    public void setSocietyId(LongFilter societyId) {
        this.societyId = societyId;
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
        final SocietyBanksDetailsCriteria that = (SocietyBanksDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(ifsccode, that.ifsccode) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(accountType, that.accountType) &&
            Objects.equals(accHeadCode, that.accHeadCode) &&
            Objects.equals(parentAccHeadCode, that.parentAccHeadCode) &&
            Objects.equals(accountName, that.accountName) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(societyId, that.societyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            bankName,
            ifsccode,
            branchName,
            accountNumber,
            isActive,
            accountType,
            accHeadCode,
            parentAccHeadCode,
            accountName,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            freeField1,
            freeField2,
            freeField3,
            societyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyBanksDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (bankName != null ? "bankName=" + bankName + ", " : "") +
            (ifsccode != null ? "ifsccode=" + ifsccode + ", " : "") +
            (branchName != null ? "branchName=" + branchName + ", " : "") +
            (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (accountType != null ? "accountType=" + accountType + ", " : "") +
            (accHeadCode != null ? "accHeadCode=" + accHeadCode + ", " : "") +
            (parentAccHeadCode != null ? "parentAccHeadCode=" + parentAccHeadCode + ", " : "") +
            (accountName != null ? "accountName=" + accountName + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (societyId != null ? "societyId=" + societyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
