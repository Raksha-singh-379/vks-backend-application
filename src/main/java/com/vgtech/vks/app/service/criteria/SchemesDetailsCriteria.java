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
 * Criteria class for the {@link com.vgtech.vks.app.domain.SchemesDetails} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.SchemesDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /schemes-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SchemesDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter fdDurationDays;

    private DoubleFilter interest;

    private IntegerFilter lockInPeriod;

    private StringFilter schemeName;

    private IntegerFilter rdDurationMonths;

    private BooleanFilter reinvestInterest;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter isDeleted;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private LongFilter societyId;

    private Boolean distinct;

    public SchemesDetailsCriteria() {}

    public SchemesDetailsCriteria(SchemesDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fdDurationDays = other.fdDurationDays == null ? null : other.fdDurationDays.copy();
        this.interest = other.interest == null ? null : other.interest.copy();
        this.lockInPeriod = other.lockInPeriod == null ? null : other.lockInPeriod.copy();
        this.schemeName = other.schemeName == null ? null : other.schemeName.copy();
        this.rdDurationMonths = other.rdDurationMonths == null ? null : other.rdDurationMonths.copy();
        this.reinvestInterest = other.reinvestInterest == null ? null : other.reinvestInterest.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.societyId = other.societyId == null ? null : other.societyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SchemesDetailsCriteria copy() {
        return new SchemesDetailsCriteria(this);
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

    public IntegerFilter getFdDurationDays() {
        return fdDurationDays;
    }

    public IntegerFilter fdDurationDays() {
        if (fdDurationDays == null) {
            fdDurationDays = new IntegerFilter();
        }
        return fdDurationDays;
    }

    public void setFdDurationDays(IntegerFilter fdDurationDays) {
        this.fdDurationDays = fdDurationDays;
    }

    public DoubleFilter getInterest() {
        return interest;
    }

    public DoubleFilter interest() {
        if (interest == null) {
            interest = new DoubleFilter();
        }
        return interest;
    }

    public void setInterest(DoubleFilter interest) {
        this.interest = interest;
    }

    public IntegerFilter getLockInPeriod() {
        return lockInPeriod;
    }

    public IntegerFilter lockInPeriod() {
        if (lockInPeriod == null) {
            lockInPeriod = new IntegerFilter();
        }
        return lockInPeriod;
    }

    public void setLockInPeriod(IntegerFilter lockInPeriod) {
        this.lockInPeriod = lockInPeriod;
    }

    public StringFilter getSchemeName() {
        return schemeName;
    }

    public StringFilter schemeName() {
        if (schemeName == null) {
            schemeName = new StringFilter();
        }
        return schemeName;
    }

    public void setSchemeName(StringFilter schemeName) {
        this.schemeName = schemeName;
    }

    public IntegerFilter getRdDurationMonths() {
        return rdDurationMonths;
    }

    public IntegerFilter rdDurationMonths() {
        if (rdDurationMonths == null) {
            rdDurationMonths = new IntegerFilter();
        }
        return rdDurationMonths;
    }

    public void setRdDurationMonths(IntegerFilter rdDurationMonths) {
        this.rdDurationMonths = rdDurationMonths;
    }

    public BooleanFilter getReinvestInterest() {
        return reinvestInterest;
    }

    public BooleanFilter reinvestInterest() {
        if (reinvestInterest == null) {
            reinvestInterest = new BooleanFilter();
        }
        return reinvestInterest;
    }

    public void setReinvestInterest(BooleanFilter reinvestInterest) {
        this.reinvestInterest = reinvestInterest;
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
        final SchemesDetailsCriteria that = (SchemesDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fdDurationDays, that.fdDurationDays) &&
            Objects.equals(interest, that.interest) &&
            Objects.equals(lockInPeriod, that.lockInPeriod) &&
            Objects.equals(schemeName, that.schemeName) &&
            Objects.equals(rdDurationMonths, that.rdDurationMonths) &&
            Objects.equals(reinvestInterest, that.reinvestInterest) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(isDeleted, that.isDeleted) &&
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
            fdDurationDays,
            interest,
            lockInPeriod,
            schemeName,
            rdDurationMonths,
            reinvestInterest,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            isDeleted,
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
        return "SchemesDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (fdDurationDays != null ? "fdDurationDays=" + fdDurationDays + ", " : "") +
            (interest != null ? "interest=" + interest + ", " : "") +
            (lockInPeriod != null ? "lockInPeriod=" + lockInPeriod + ", " : "") +
            (schemeName != null ? "schemeName=" + schemeName + ", " : "") +
            (rdDurationMonths != null ? "rdDurationMonths=" + rdDurationMonths + ", " : "") +
            (reinvestInterest != null ? "reinvestInterest=" + reinvestInterest + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (societyId != null ? "societyId=" + societyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
