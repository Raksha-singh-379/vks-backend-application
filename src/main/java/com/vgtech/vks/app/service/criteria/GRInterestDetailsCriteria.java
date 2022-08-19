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
 * Criteria class for the {@link com.vgtech.vks.app.domain.GRInterestDetails} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.GRInterestDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /gr-interest-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class GRInterestDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter loanGrName;

    private StringFilter criteria;

    private StringFilter productType;

    private BooleanFilter isActivated;

    private DoubleFilter borrowingInterestRate;

    private DoubleFilter interestOnLoan;

    private DoubleFilter penaltyInterest;

    private DoubleFilter surcharge;

    private DoubleFilter loanDuration;

    private IntegerFilter numberOFInstallment;

    private DoubleFilter extendedInterstRate;

    private DoubleFilter centralGovSubsidyInterest;

    private DoubleFilter distBankSubsidyInterest;

    private DoubleFilter borrowerInterest;

    private DoubleFilter stateGovSubsidyInterest;

    private StringFilter year;

    private InstantFilter startDate;

    private InstantFilter endDate;

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

    public GRInterestDetailsCriteria() {}

    public GRInterestDetailsCriteria(GRInterestDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.loanGrName = other.loanGrName == null ? null : other.loanGrName.copy();
        this.criteria = other.criteria == null ? null : other.criteria.copy();
        this.productType = other.productType == null ? null : other.productType.copy();
        this.isActivated = other.isActivated == null ? null : other.isActivated.copy();
        this.borrowingInterestRate = other.borrowingInterestRate == null ? null : other.borrowingInterestRate.copy();
        this.interestOnLoan = other.interestOnLoan == null ? null : other.interestOnLoan.copy();
        this.penaltyInterest = other.penaltyInterest == null ? null : other.penaltyInterest.copy();
        this.surcharge = other.surcharge == null ? null : other.surcharge.copy();
        this.loanDuration = other.loanDuration == null ? null : other.loanDuration.copy();
        this.numberOFInstallment = other.numberOFInstallment == null ? null : other.numberOFInstallment.copy();
        this.extendedInterstRate = other.extendedInterstRate == null ? null : other.extendedInterstRate.copy();
        this.centralGovSubsidyInterest = other.centralGovSubsidyInterest == null ? null : other.centralGovSubsidyInterest.copy();
        this.distBankSubsidyInterest = other.distBankSubsidyInterest == null ? null : other.distBankSubsidyInterest.copy();
        this.borrowerInterest = other.borrowerInterest == null ? null : other.borrowerInterest.copy();
        this.stateGovSubsidyInterest = other.stateGovSubsidyInterest == null ? null : other.stateGovSubsidyInterest.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
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
    public GRInterestDetailsCriteria copy() {
        return new GRInterestDetailsCriteria(this);
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

    public StringFilter getLoanGrName() {
        return loanGrName;
    }

    public StringFilter loanGrName() {
        if (loanGrName == null) {
            loanGrName = new StringFilter();
        }
        return loanGrName;
    }

    public void setLoanGrName(StringFilter loanGrName) {
        this.loanGrName = loanGrName;
    }

    public StringFilter getCriteria() {
        return criteria;
    }

    public StringFilter criteria() {
        if (criteria == null) {
            criteria = new StringFilter();
        }
        return criteria;
    }

    public void setCriteria(StringFilter criteria) {
        this.criteria = criteria;
    }

    public StringFilter getProductType() {
        return productType;
    }

    public StringFilter productType() {
        if (productType == null) {
            productType = new StringFilter();
        }
        return productType;
    }

    public void setProductType(StringFilter productType) {
        this.productType = productType;
    }

    public BooleanFilter getIsActivated() {
        return isActivated;
    }

    public BooleanFilter isActivated() {
        if (isActivated == null) {
            isActivated = new BooleanFilter();
        }
        return isActivated;
    }

    public void setIsActivated(BooleanFilter isActivated) {
        this.isActivated = isActivated;
    }

    public DoubleFilter getBorrowingInterestRate() {
        return borrowingInterestRate;
    }

    public DoubleFilter borrowingInterestRate() {
        if (borrowingInterestRate == null) {
            borrowingInterestRate = new DoubleFilter();
        }
        return borrowingInterestRate;
    }

    public void setBorrowingInterestRate(DoubleFilter borrowingInterestRate) {
        this.borrowingInterestRate = borrowingInterestRate;
    }

    public DoubleFilter getInterestOnLoan() {
        return interestOnLoan;
    }

    public DoubleFilter interestOnLoan() {
        if (interestOnLoan == null) {
            interestOnLoan = new DoubleFilter();
        }
        return interestOnLoan;
    }

    public void setInterestOnLoan(DoubleFilter interestOnLoan) {
        this.interestOnLoan = interestOnLoan;
    }

    public DoubleFilter getPenaltyInterest() {
        return penaltyInterest;
    }

    public DoubleFilter penaltyInterest() {
        if (penaltyInterest == null) {
            penaltyInterest = new DoubleFilter();
        }
        return penaltyInterest;
    }

    public void setPenaltyInterest(DoubleFilter penaltyInterest) {
        this.penaltyInterest = penaltyInterest;
    }

    public DoubleFilter getSurcharge() {
        return surcharge;
    }

    public DoubleFilter surcharge() {
        if (surcharge == null) {
            surcharge = new DoubleFilter();
        }
        return surcharge;
    }

    public void setSurcharge(DoubleFilter surcharge) {
        this.surcharge = surcharge;
    }

    public DoubleFilter getLoanDuration() {
        return loanDuration;
    }

    public DoubleFilter loanDuration() {
        if (loanDuration == null) {
            loanDuration = new DoubleFilter();
        }
        return loanDuration;
    }

    public void setLoanDuration(DoubleFilter loanDuration) {
        this.loanDuration = loanDuration;
    }

    public IntegerFilter getNumberOFInstallment() {
        return numberOFInstallment;
    }

    public IntegerFilter numberOFInstallment() {
        if (numberOFInstallment == null) {
            numberOFInstallment = new IntegerFilter();
        }
        return numberOFInstallment;
    }

    public void setNumberOFInstallment(IntegerFilter numberOFInstallment) {
        this.numberOFInstallment = numberOFInstallment;
    }

    public DoubleFilter getExtendedInterstRate() {
        return extendedInterstRate;
    }

    public DoubleFilter extendedInterstRate() {
        if (extendedInterstRate == null) {
            extendedInterstRate = new DoubleFilter();
        }
        return extendedInterstRate;
    }

    public void setExtendedInterstRate(DoubleFilter extendedInterstRate) {
        this.extendedInterstRate = extendedInterstRate;
    }

    public DoubleFilter getCentralGovSubsidyInterest() {
        return centralGovSubsidyInterest;
    }

    public DoubleFilter centralGovSubsidyInterest() {
        if (centralGovSubsidyInterest == null) {
            centralGovSubsidyInterest = new DoubleFilter();
        }
        return centralGovSubsidyInterest;
    }

    public void setCentralGovSubsidyInterest(DoubleFilter centralGovSubsidyInterest) {
        this.centralGovSubsidyInterest = centralGovSubsidyInterest;
    }

    public DoubleFilter getDistBankSubsidyInterest() {
        return distBankSubsidyInterest;
    }

    public DoubleFilter distBankSubsidyInterest() {
        if (distBankSubsidyInterest == null) {
            distBankSubsidyInterest = new DoubleFilter();
        }
        return distBankSubsidyInterest;
    }

    public void setDistBankSubsidyInterest(DoubleFilter distBankSubsidyInterest) {
        this.distBankSubsidyInterest = distBankSubsidyInterest;
    }

    public DoubleFilter getBorrowerInterest() {
        return borrowerInterest;
    }

    public DoubleFilter borrowerInterest() {
        if (borrowerInterest == null) {
            borrowerInterest = new DoubleFilter();
        }
        return borrowerInterest;
    }

    public void setBorrowerInterest(DoubleFilter borrowerInterest) {
        this.borrowerInterest = borrowerInterest;
    }

    public DoubleFilter getStateGovSubsidyInterest() {
        return stateGovSubsidyInterest;
    }

    public DoubleFilter stateGovSubsidyInterest() {
        if (stateGovSubsidyInterest == null) {
            stateGovSubsidyInterest = new DoubleFilter();
        }
        return stateGovSubsidyInterest;
    }

    public void setStateGovSubsidyInterest(DoubleFilter stateGovSubsidyInterest) {
        this.stateGovSubsidyInterest = stateGovSubsidyInterest;
    }

    public StringFilter getYear() {
        return year;
    }

    public StringFilter year() {
        if (year == null) {
            year = new StringFilter();
        }
        return year;
    }

    public void setYear(StringFilter year) {
        this.year = year;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public InstantFilter startDate() {
        if (startDate == null) {
            startDate = new InstantFilter();
        }
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public InstantFilter endDate() {
        if (endDate == null) {
            endDate = new InstantFilter();
        }
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
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
        final GRInterestDetailsCriteria that = (GRInterestDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(loanGrName, that.loanGrName) &&
            Objects.equals(criteria, that.criteria) &&
            Objects.equals(productType, that.productType) &&
            Objects.equals(isActivated, that.isActivated) &&
            Objects.equals(borrowingInterestRate, that.borrowingInterestRate) &&
            Objects.equals(interestOnLoan, that.interestOnLoan) &&
            Objects.equals(penaltyInterest, that.penaltyInterest) &&
            Objects.equals(surcharge, that.surcharge) &&
            Objects.equals(loanDuration, that.loanDuration) &&
            Objects.equals(numberOFInstallment, that.numberOFInstallment) &&
            Objects.equals(extendedInterstRate, that.extendedInterstRate) &&
            Objects.equals(centralGovSubsidyInterest, that.centralGovSubsidyInterest) &&
            Objects.equals(distBankSubsidyInterest, that.distBankSubsidyInterest) &&
            Objects.equals(borrowerInterest, that.borrowerInterest) &&
            Objects.equals(stateGovSubsidyInterest, that.stateGovSubsidyInterest) &&
            Objects.equals(year, that.year) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
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
            loanGrName,
            criteria,
            productType,
            isActivated,
            borrowingInterestRate,
            interestOnLoan,
            penaltyInterest,
            surcharge,
            loanDuration,
            numberOFInstallment,
            extendedInterstRate,
            centralGovSubsidyInterest,
            distBankSubsidyInterest,
            borrowerInterest,
            stateGovSubsidyInterest,
            year,
            startDate,
            endDate,
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
        return "GRInterestDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (loanGrName != null ? "loanGrName=" + loanGrName + ", " : "") +
            (criteria != null ? "criteria=" + criteria + ", " : "") +
            (productType != null ? "productType=" + productType + ", " : "") +
            (isActivated != null ? "isActivated=" + isActivated + ", " : "") +
            (borrowingInterestRate != null ? "borrowingInterestRate=" + borrowingInterestRate + ", " : "") +
            (interestOnLoan != null ? "interestOnLoan=" + interestOnLoan + ", " : "") +
            (penaltyInterest != null ? "penaltyInterest=" + penaltyInterest + ", " : "") +
            (surcharge != null ? "surcharge=" + surcharge + ", " : "") +
            (loanDuration != null ? "loanDuration=" + loanDuration + ", " : "") +
            (numberOFInstallment != null ? "numberOFInstallment=" + numberOFInstallment + ", " : "") +
            (extendedInterstRate != null ? "extendedInterstRate=" + extendedInterstRate + ", " : "") +
            (centralGovSubsidyInterest != null ? "centralGovSubsidyInterest=" + centralGovSubsidyInterest + ", " : "") +
            (distBankSubsidyInterest != null ? "distBankSubsidyInterest=" + distBankSubsidyInterest + ", " : "") +
            (borrowerInterest != null ? "borrowerInterest=" + borrowerInterest + ", " : "") +
            (stateGovSubsidyInterest != null ? "stateGovSubsidyInterest=" + stateGovSubsidyInterest + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
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
