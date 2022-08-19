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
 * Criteria class for the {@link com.vgtech.vks.app.domain.SocietyLoanProduct} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.SocietyLoanProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /society-loan-products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SocietyLoanProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productName;

    private StringFilter accHeadCode;

    private DoubleFilter borrowingInterestRate;

    private IntegerFilter duration;

    private DoubleFilter interestRate;

    private InstantFilter lastDateOfRepayment;

    private StringFilter loanNumber;

    private DoubleFilter maxLoanAmt;

    private IntegerFilter noOfDisbursement;

    private IntegerFilter noOfInstallment;

    private StringFilter parentAccHeadCode;

    private LongFilter parentAccHeadId;

    private DoubleFilter penaltyInterest;

    private StringFilter productType;

    private InstantFilter resolutionDate;

    private StringFilter resolutionNo;

    private StringFilter status;

    private DoubleFilter surcharge;

    private LongFilter unitSize;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private InstantFilter createdOn;

    private StringFilter createdBy;

    private BooleanFilter isActivate;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private LongFilter societyId;

    private LongFilter bankDhoranDetailsId;

    private LongFilter gRInterestDetailsId;

    private Boolean distinct;

    public SocietyLoanProductCriteria() {}

    public SocietyLoanProductCriteria(SocietyLoanProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.productName = other.productName == null ? null : other.productName.copy();
        this.accHeadCode = other.accHeadCode == null ? null : other.accHeadCode.copy();
        this.borrowingInterestRate = other.borrowingInterestRate == null ? null : other.borrowingInterestRate.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.interestRate = other.interestRate == null ? null : other.interestRate.copy();
        this.lastDateOfRepayment = other.lastDateOfRepayment == null ? null : other.lastDateOfRepayment.copy();
        this.loanNumber = other.loanNumber == null ? null : other.loanNumber.copy();
        this.maxLoanAmt = other.maxLoanAmt == null ? null : other.maxLoanAmt.copy();
        this.noOfDisbursement = other.noOfDisbursement == null ? null : other.noOfDisbursement.copy();
        this.noOfInstallment = other.noOfInstallment == null ? null : other.noOfInstallment.copy();
        this.parentAccHeadCode = other.parentAccHeadCode == null ? null : other.parentAccHeadCode.copy();
        this.parentAccHeadId = other.parentAccHeadId == null ? null : other.parentAccHeadId.copy();
        this.penaltyInterest = other.penaltyInterest == null ? null : other.penaltyInterest.copy();
        this.productType = other.productType == null ? null : other.productType.copy();
        this.resolutionDate = other.resolutionDate == null ? null : other.resolutionDate.copy();
        this.resolutionNo = other.resolutionNo == null ? null : other.resolutionNo.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.surcharge = other.surcharge == null ? null : other.surcharge.copy();
        this.unitSize = other.unitSize == null ? null : other.unitSize.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.isActivate = other.isActivate == null ? null : other.isActivate.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.societyId = other.societyId == null ? null : other.societyId.copy();
        this.bankDhoranDetailsId = other.bankDhoranDetailsId == null ? null : other.bankDhoranDetailsId.copy();
        this.gRInterestDetailsId = other.gRInterestDetailsId == null ? null : other.gRInterestDetailsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SocietyLoanProductCriteria copy() {
        return new SocietyLoanProductCriteria(this);
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

    public StringFilter getProductName() {
        return productName;
    }

    public StringFilter productName() {
        if (productName == null) {
            productName = new StringFilter();
        }
        return productName;
    }

    public void setProductName(StringFilter productName) {
        this.productName = productName;
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

    public IntegerFilter getDuration() {
        return duration;
    }

    public IntegerFilter duration() {
        if (duration == null) {
            duration = new IntegerFilter();
        }
        return duration;
    }

    public void setDuration(IntegerFilter duration) {
        this.duration = duration;
    }

    public DoubleFilter getInterestRate() {
        return interestRate;
    }

    public DoubleFilter interestRate() {
        if (interestRate == null) {
            interestRate = new DoubleFilter();
        }
        return interestRate;
    }

    public void setInterestRate(DoubleFilter interestRate) {
        this.interestRate = interestRate;
    }

    public InstantFilter getLastDateOfRepayment() {
        return lastDateOfRepayment;
    }

    public InstantFilter lastDateOfRepayment() {
        if (lastDateOfRepayment == null) {
            lastDateOfRepayment = new InstantFilter();
        }
        return lastDateOfRepayment;
    }

    public void setLastDateOfRepayment(InstantFilter lastDateOfRepayment) {
        this.lastDateOfRepayment = lastDateOfRepayment;
    }

    public StringFilter getLoanNumber() {
        return loanNumber;
    }

    public StringFilter loanNumber() {
        if (loanNumber == null) {
            loanNumber = new StringFilter();
        }
        return loanNumber;
    }

    public void setLoanNumber(StringFilter loanNumber) {
        this.loanNumber = loanNumber;
    }

    public DoubleFilter getMaxLoanAmt() {
        return maxLoanAmt;
    }

    public DoubleFilter maxLoanAmt() {
        if (maxLoanAmt == null) {
            maxLoanAmt = new DoubleFilter();
        }
        return maxLoanAmt;
    }

    public void setMaxLoanAmt(DoubleFilter maxLoanAmt) {
        this.maxLoanAmt = maxLoanAmt;
    }

    public IntegerFilter getNoOfDisbursement() {
        return noOfDisbursement;
    }

    public IntegerFilter noOfDisbursement() {
        if (noOfDisbursement == null) {
            noOfDisbursement = new IntegerFilter();
        }
        return noOfDisbursement;
    }

    public void setNoOfDisbursement(IntegerFilter noOfDisbursement) {
        this.noOfDisbursement = noOfDisbursement;
    }

    public IntegerFilter getNoOfInstallment() {
        return noOfInstallment;
    }

    public IntegerFilter noOfInstallment() {
        if (noOfInstallment == null) {
            noOfInstallment = new IntegerFilter();
        }
        return noOfInstallment;
    }

    public void setNoOfInstallment(IntegerFilter noOfInstallment) {
        this.noOfInstallment = noOfInstallment;
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

    public LongFilter getParentAccHeadId() {
        return parentAccHeadId;
    }

    public LongFilter parentAccHeadId() {
        if (parentAccHeadId == null) {
            parentAccHeadId = new LongFilter();
        }
        return parentAccHeadId;
    }

    public void setParentAccHeadId(LongFilter parentAccHeadId) {
        this.parentAccHeadId = parentAccHeadId;
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

    public InstantFilter getResolutionDate() {
        return resolutionDate;
    }

    public InstantFilter resolutionDate() {
        if (resolutionDate == null) {
            resolutionDate = new InstantFilter();
        }
        return resolutionDate;
    }

    public void setResolutionDate(InstantFilter resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public StringFilter getResolutionNo() {
        return resolutionNo;
    }

    public StringFilter resolutionNo() {
        if (resolutionNo == null) {
            resolutionNo = new StringFilter();
        }
        return resolutionNo;
    }

    public void setResolutionNo(StringFilter resolutionNo) {
        this.resolutionNo = resolutionNo;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
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

    public LongFilter getUnitSize() {
        return unitSize;
    }

    public LongFilter unitSize() {
        if (unitSize == null) {
            unitSize = new LongFilter();
        }
        return unitSize;
    }

    public void setUnitSize(LongFilter unitSize) {
        this.unitSize = unitSize;
    }

    public InstantFilter getValidFrom() {
        return validFrom;
    }

    public InstantFilter validFrom() {
        if (validFrom == null) {
            validFrom = new InstantFilter();
        }
        return validFrom;
    }

    public void setValidFrom(InstantFilter validFrom) {
        this.validFrom = validFrom;
    }

    public InstantFilter getValidTo() {
        return validTo;
    }

    public InstantFilter validTo() {
        if (validTo == null) {
            validTo = new InstantFilter();
        }
        return validTo;
    }

    public void setValidTo(InstantFilter validTo) {
        this.validTo = validTo;
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

    public BooleanFilter getIsActivate() {
        return isActivate;
    }

    public BooleanFilter isActivate() {
        if (isActivate == null) {
            isActivate = new BooleanFilter();
        }
        return isActivate;
    }

    public void setIsActivate(BooleanFilter isActivate) {
        this.isActivate = isActivate;
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

    public LongFilter getBankDhoranDetailsId() {
        return bankDhoranDetailsId;
    }

    public LongFilter bankDhoranDetailsId() {
        if (bankDhoranDetailsId == null) {
            bankDhoranDetailsId = new LongFilter();
        }
        return bankDhoranDetailsId;
    }

    public void setBankDhoranDetailsId(LongFilter bankDhoranDetailsId) {
        this.bankDhoranDetailsId = bankDhoranDetailsId;
    }

    public LongFilter getGRInterestDetailsId() {
        return gRInterestDetailsId;
    }

    public LongFilter gRInterestDetailsId() {
        if (gRInterestDetailsId == null) {
            gRInterestDetailsId = new LongFilter();
        }
        return gRInterestDetailsId;
    }

    public void setGRInterestDetailsId(LongFilter gRInterestDetailsId) {
        this.gRInterestDetailsId = gRInterestDetailsId;
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
        final SocietyLoanProductCriteria that = (SocietyLoanProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(productName, that.productName) &&
            Objects.equals(accHeadCode, that.accHeadCode) &&
            Objects.equals(borrowingInterestRate, that.borrowingInterestRate) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(interestRate, that.interestRate) &&
            Objects.equals(lastDateOfRepayment, that.lastDateOfRepayment) &&
            Objects.equals(loanNumber, that.loanNumber) &&
            Objects.equals(maxLoanAmt, that.maxLoanAmt) &&
            Objects.equals(noOfDisbursement, that.noOfDisbursement) &&
            Objects.equals(noOfInstallment, that.noOfInstallment) &&
            Objects.equals(parentAccHeadCode, that.parentAccHeadCode) &&
            Objects.equals(parentAccHeadId, that.parentAccHeadId) &&
            Objects.equals(penaltyInterest, that.penaltyInterest) &&
            Objects.equals(productType, that.productType) &&
            Objects.equals(resolutionDate, that.resolutionDate) &&
            Objects.equals(resolutionNo, that.resolutionNo) &&
            Objects.equals(status, that.status) &&
            Objects.equals(surcharge, that.surcharge) &&
            Objects.equals(unitSize, that.unitSize) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(isActivate, that.isActivate) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(societyId, that.societyId) &&
            Objects.equals(bankDhoranDetailsId, that.bankDhoranDetailsId) &&
            Objects.equals(gRInterestDetailsId, that.gRInterestDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            productName,
            accHeadCode,
            borrowingInterestRate,
            duration,
            interestRate,
            lastDateOfRepayment,
            loanNumber,
            maxLoanAmt,
            noOfDisbursement,
            noOfInstallment,
            parentAccHeadCode,
            parentAccHeadId,
            penaltyInterest,
            productType,
            resolutionDate,
            resolutionNo,
            status,
            surcharge,
            unitSize,
            validFrom,
            validTo,
            createdOn,
            createdBy,
            isActivate,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            societyId,
            bankDhoranDetailsId,
            gRInterestDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyLoanProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (productName != null ? "productName=" + productName + ", " : "") +
            (accHeadCode != null ? "accHeadCode=" + accHeadCode + ", " : "") +
            (borrowingInterestRate != null ? "borrowingInterestRate=" + borrowingInterestRate + ", " : "") +
            (duration != null ? "duration=" + duration + ", " : "") +
            (interestRate != null ? "interestRate=" + interestRate + ", " : "") +
            (lastDateOfRepayment != null ? "lastDateOfRepayment=" + lastDateOfRepayment + ", " : "") +
            (loanNumber != null ? "loanNumber=" + loanNumber + ", " : "") +
            (maxLoanAmt != null ? "maxLoanAmt=" + maxLoanAmt + ", " : "") +
            (noOfDisbursement != null ? "noOfDisbursement=" + noOfDisbursement + ", " : "") +
            (noOfInstallment != null ? "noOfInstallment=" + noOfInstallment + ", " : "") +
            (parentAccHeadCode != null ? "parentAccHeadCode=" + parentAccHeadCode + ", " : "") +
            (parentAccHeadId != null ? "parentAccHeadId=" + parentAccHeadId + ", " : "") +
            (penaltyInterest != null ? "penaltyInterest=" + penaltyInterest + ", " : "") +
            (productType != null ? "productType=" + productType + ", " : "") +
            (resolutionDate != null ? "resolutionDate=" + resolutionDate + ", " : "") +
            (resolutionNo != null ? "resolutionNo=" + resolutionNo + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (surcharge != null ? "surcharge=" + surcharge + ", " : "") +
            (unitSize != null ? "unitSize=" + unitSize + ", " : "") +
            (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
            (validTo != null ? "validTo=" + validTo + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (isActivate != null ? "isActivate=" + isActivate + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (societyId != null ? "societyId=" + societyId + ", " : "") +
            (bankDhoranDetailsId != null ? "bankDhoranDetailsId=" + bankDhoranDetailsId + ", " : "") +
            (gRInterestDetailsId != null ? "gRInterestDetailsId=" + gRInterestDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
