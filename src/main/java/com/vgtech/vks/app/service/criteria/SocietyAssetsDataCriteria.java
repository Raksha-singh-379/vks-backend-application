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
 * Criteria class for the {@link com.vgtech.vks.app.domain.SocietyAssetsData} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.SocietyAssetsDataResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /society-assets-data?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SocietyAssetsDataCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter amount;

    private LongFilter balanceQuantity;

    private DoubleFilter balanceValue;

    private StringFilter billNo;

    private StringFilter mode;

    private DoubleFilter cost;

    private StringFilter transactionType;

    private InstantFilter transactionDate;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter isDeleted;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private LongFilter societyAssetsId;

    private Boolean distinct;

    public SocietyAssetsDataCriteria() {}

    public SocietyAssetsDataCriteria(SocietyAssetsDataCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.balanceQuantity = other.balanceQuantity == null ? null : other.balanceQuantity.copy();
        this.balanceValue = other.balanceValue == null ? null : other.balanceValue.copy();
        this.billNo = other.billNo == null ? null : other.billNo.copy();
        this.mode = other.mode == null ? null : other.mode.copy();
        this.cost = other.cost == null ? null : other.cost.copy();
        this.transactionType = other.transactionType == null ? null : other.transactionType.copy();
        this.transactionDate = other.transactionDate == null ? null : other.transactionDate.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.societyAssetsId = other.societyAssetsId == null ? null : other.societyAssetsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SocietyAssetsDataCriteria copy() {
        return new SocietyAssetsDataCriteria(this);
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

    public DoubleFilter getAmount() {
        return amount;
    }

    public DoubleFilter amount() {
        if (amount == null) {
            amount = new DoubleFilter();
        }
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public LongFilter getBalanceQuantity() {
        return balanceQuantity;
    }

    public LongFilter balanceQuantity() {
        if (balanceQuantity == null) {
            balanceQuantity = new LongFilter();
        }
        return balanceQuantity;
    }

    public void setBalanceQuantity(LongFilter balanceQuantity) {
        this.balanceQuantity = balanceQuantity;
    }

    public DoubleFilter getBalanceValue() {
        return balanceValue;
    }

    public DoubleFilter balanceValue() {
        if (balanceValue == null) {
            balanceValue = new DoubleFilter();
        }
        return balanceValue;
    }

    public void setBalanceValue(DoubleFilter balanceValue) {
        this.balanceValue = balanceValue;
    }

    public StringFilter getBillNo() {
        return billNo;
    }

    public StringFilter billNo() {
        if (billNo == null) {
            billNo = new StringFilter();
        }
        return billNo;
    }

    public void setBillNo(StringFilter billNo) {
        this.billNo = billNo;
    }

    public StringFilter getMode() {
        return mode;
    }

    public StringFilter mode() {
        if (mode == null) {
            mode = new StringFilter();
        }
        return mode;
    }

    public void setMode(StringFilter mode) {
        this.mode = mode;
    }

    public DoubleFilter getCost() {
        return cost;
    }

    public DoubleFilter cost() {
        if (cost == null) {
            cost = new DoubleFilter();
        }
        return cost;
    }

    public void setCost(DoubleFilter cost) {
        this.cost = cost;
    }

    public StringFilter getTransactionType() {
        return transactionType;
    }

    public StringFilter transactionType() {
        if (transactionType == null) {
            transactionType = new StringFilter();
        }
        return transactionType;
    }

    public void setTransactionType(StringFilter transactionType) {
        this.transactionType = transactionType;
    }

    public InstantFilter getTransactionDate() {
        return transactionDate;
    }

    public InstantFilter transactionDate() {
        if (transactionDate == null) {
            transactionDate = new InstantFilter();
        }
        return transactionDate;
    }

    public void setTransactionDate(InstantFilter transactionDate) {
        this.transactionDate = transactionDate;
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

    public LongFilter getSocietyAssetsId() {
        return societyAssetsId;
    }

    public LongFilter societyAssetsId() {
        if (societyAssetsId == null) {
            societyAssetsId = new LongFilter();
        }
        return societyAssetsId;
    }

    public void setSocietyAssetsId(LongFilter societyAssetsId) {
        this.societyAssetsId = societyAssetsId;
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
        final SocietyAssetsDataCriteria that = (SocietyAssetsDataCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(balanceQuantity, that.balanceQuantity) &&
            Objects.equals(balanceValue, that.balanceValue) &&
            Objects.equals(billNo, that.billNo) &&
            Objects.equals(mode, that.mode) &&
            Objects.equals(cost, that.cost) &&
            Objects.equals(transactionType, that.transactionType) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(societyAssetsId, that.societyAssetsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            amount,
            balanceQuantity,
            balanceValue,
            billNo,
            mode,
            cost,
            transactionType,
            transactionDate,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            isDeleted,
            freeField1,
            freeField2,
            freeField3,
            societyAssetsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyAssetsDataCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (balanceQuantity != null ? "balanceQuantity=" + balanceQuantity + ", " : "") +
            (balanceValue != null ? "balanceValue=" + balanceValue + ", " : "") +
            (billNo != null ? "billNo=" + billNo + ", " : "") +
            (mode != null ? "mode=" + mode + ", " : "") +
            (cost != null ? "cost=" + cost + ", " : "") +
            (transactionType != null ? "transactionType=" + transactionType + ", " : "") +
            (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (societyAssetsId != null ? "societyAssetsId=" + societyAssetsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
