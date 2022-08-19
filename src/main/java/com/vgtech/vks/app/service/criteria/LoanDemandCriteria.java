package com.vgtech.vks.app.service.criteria;

import com.vgtech.vks.app.domain.enumeration.LoanStatus;
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
 * Criteria class for the {@link com.vgtech.vks.app.domain.LoanDemand} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.LoanDemandResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /loan-demands?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class LoanDemandCriteria implements Serializable, Criteria {

    /**
     * Class for filtering LoanStatus
     */
    public static class LoanStatusFilter extends Filter<LoanStatus> {

        public LoanStatusFilter() {}

        public LoanStatusFilter(LoanStatusFilter filter) {
            super(filter);
        }

        @Override
        public LoanStatusFilter copy() {
            return new LoanStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter loanDemandAmount;

    private DoubleFilter maxAllowedAmount;

    private DoubleFilter adjustedDemand;

    private DoubleFilter annualIncome;

    private DoubleFilter costOfInvestment;

    private IntegerFilter demandedLandAreaInHector;

    private LoanStatusFilter status;

    private StringFilter seasonOfCropLoan;

    private StringFilter year;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private LongFilter memberId;

    private LongFilter societyLoanProductId;

    private LongFilter memberLandAssetsId;

    private LongFilter societyCropRegistrationId;

    private Boolean distinct;

    public LoanDemandCriteria() {}

    public LoanDemandCriteria(LoanDemandCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.loanDemandAmount = other.loanDemandAmount == null ? null : other.loanDemandAmount.copy();
        this.maxAllowedAmount = other.maxAllowedAmount == null ? null : other.maxAllowedAmount.copy();
        this.adjustedDemand = other.adjustedDemand == null ? null : other.adjustedDemand.copy();
        this.annualIncome = other.annualIncome == null ? null : other.annualIncome.copy();
        this.costOfInvestment = other.costOfInvestment == null ? null : other.costOfInvestment.copy();
        this.demandedLandAreaInHector = other.demandedLandAreaInHector == null ? null : other.demandedLandAreaInHector.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.seasonOfCropLoan = other.seasonOfCropLoan == null ? null : other.seasonOfCropLoan.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.societyLoanProductId = other.societyLoanProductId == null ? null : other.societyLoanProductId.copy();
        this.memberLandAssetsId = other.memberLandAssetsId == null ? null : other.memberLandAssetsId.copy();
        this.societyCropRegistrationId = other.societyCropRegistrationId == null ? null : other.societyCropRegistrationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LoanDemandCriteria copy() {
        return new LoanDemandCriteria(this);
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

    public DoubleFilter getLoanDemandAmount() {
        return loanDemandAmount;
    }

    public DoubleFilter loanDemandAmount() {
        if (loanDemandAmount == null) {
            loanDemandAmount = new DoubleFilter();
        }
        return loanDemandAmount;
    }

    public void setLoanDemandAmount(DoubleFilter loanDemandAmount) {
        this.loanDemandAmount = loanDemandAmount;
    }

    public DoubleFilter getMaxAllowedAmount() {
        return maxAllowedAmount;
    }

    public DoubleFilter maxAllowedAmount() {
        if (maxAllowedAmount == null) {
            maxAllowedAmount = new DoubleFilter();
        }
        return maxAllowedAmount;
    }

    public void setMaxAllowedAmount(DoubleFilter maxAllowedAmount) {
        this.maxAllowedAmount = maxAllowedAmount;
    }

    public DoubleFilter getAdjustedDemand() {
        return adjustedDemand;
    }

    public DoubleFilter adjustedDemand() {
        if (adjustedDemand == null) {
            adjustedDemand = new DoubleFilter();
        }
        return adjustedDemand;
    }

    public void setAdjustedDemand(DoubleFilter adjustedDemand) {
        this.adjustedDemand = adjustedDemand;
    }

    public DoubleFilter getAnnualIncome() {
        return annualIncome;
    }

    public DoubleFilter annualIncome() {
        if (annualIncome == null) {
            annualIncome = new DoubleFilter();
        }
        return annualIncome;
    }

    public void setAnnualIncome(DoubleFilter annualIncome) {
        this.annualIncome = annualIncome;
    }

    public DoubleFilter getCostOfInvestment() {
        return costOfInvestment;
    }

    public DoubleFilter costOfInvestment() {
        if (costOfInvestment == null) {
            costOfInvestment = new DoubleFilter();
        }
        return costOfInvestment;
    }

    public void setCostOfInvestment(DoubleFilter costOfInvestment) {
        this.costOfInvestment = costOfInvestment;
    }

    public IntegerFilter getDemandedLandAreaInHector() {
        return demandedLandAreaInHector;
    }

    public IntegerFilter demandedLandAreaInHector() {
        if (demandedLandAreaInHector == null) {
            demandedLandAreaInHector = new IntegerFilter();
        }
        return demandedLandAreaInHector;
    }

    public void setDemandedLandAreaInHector(IntegerFilter demandedLandAreaInHector) {
        this.demandedLandAreaInHector = demandedLandAreaInHector;
    }

    public LoanStatusFilter getStatus() {
        return status;
    }

    public LoanStatusFilter status() {
        if (status == null) {
            status = new LoanStatusFilter();
        }
        return status;
    }

    public void setStatus(LoanStatusFilter status) {
        this.status = status;
    }

    public StringFilter getSeasonOfCropLoan() {
        return seasonOfCropLoan;
    }

    public StringFilter seasonOfCropLoan() {
        if (seasonOfCropLoan == null) {
            seasonOfCropLoan = new StringFilter();
        }
        return seasonOfCropLoan;
    }

    public void setSeasonOfCropLoan(StringFilter seasonOfCropLoan) {
        this.seasonOfCropLoan = seasonOfCropLoan;
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

    public LongFilter getMemberId() {
        return memberId;
    }

    public LongFilter memberId() {
        if (memberId == null) {
            memberId = new LongFilter();
        }
        return memberId;
    }

    public void setMemberId(LongFilter memberId) {
        this.memberId = memberId;
    }

    public LongFilter getSocietyLoanProductId() {
        return societyLoanProductId;
    }

    public LongFilter societyLoanProductId() {
        if (societyLoanProductId == null) {
            societyLoanProductId = new LongFilter();
        }
        return societyLoanProductId;
    }

    public void setSocietyLoanProductId(LongFilter societyLoanProductId) {
        this.societyLoanProductId = societyLoanProductId;
    }

    public LongFilter getMemberLandAssetsId() {
        return memberLandAssetsId;
    }

    public LongFilter memberLandAssetsId() {
        if (memberLandAssetsId == null) {
            memberLandAssetsId = new LongFilter();
        }
        return memberLandAssetsId;
    }

    public void setMemberLandAssetsId(LongFilter memberLandAssetsId) {
        this.memberLandAssetsId = memberLandAssetsId;
    }

    public LongFilter getSocietyCropRegistrationId() {
        return societyCropRegistrationId;
    }

    public LongFilter societyCropRegistrationId() {
        if (societyCropRegistrationId == null) {
            societyCropRegistrationId = new LongFilter();
        }
        return societyCropRegistrationId;
    }

    public void setSocietyCropRegistrationId(LongFilter societyCropRegistrationId) {
        this.societyCropRegistrationId = societyCropRegistrationId;
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
        final LoanDemandCriteria that = (LoanDemandCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(loanDemandAmount, that.loanDemandAmount) &&
            Objects.equals(maxAllowedAmount, that.maxAllowedAmount) &&
            Objects.equals(adjustedDemand, that.adjustedDemand) &&
            Objects.equals(annualIncome, that.annualIncome) &&
            Objects.equals(costOfInvestment, that.costOfInvestment) &&
            Objects.equals(demandedLandAreaInHector, that.demandedLandAreaInHector) &&
            Objects.equals(status, that.status) &&
            Objects.equals(seasonOfCropLoan, that.seasonOfCropLoan) &&
            Objects.equals(year, that.year) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(societyLoanProductId, that.societyLoanProductId) &&
            Objects.equals(memberLandAssetsId, that.memberLandAssetsId) &&
            Objects.equals(societyCropRegistrationId, that.societyCropRegistrationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            loanDemandAmount,
            maxAllowedAmount,
            adjustedDemand,
            annualIncome,
            costOfInvestment,
            demandedLandAreaInHector,
            status,
            seasonOfCropLoan,
            year,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            memberId,
            societyLoanProductId,
            memberLandAssetsId,
            societyCropRegistrationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanDemandCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (loanDemandAmount != null ? "loanDemandAmount=" + loanDemandAmount + ", " : "") +
            (maxAllowedAmount != null ? "maxAllowedAmount=" + maxAllowedAmount + ", " : "") +
            (adjustedDemand != null ? "adjustedDemand=" + adjustedDemand + ", " : "") +
            (annualIncome != null ? "annualIncome=" + annualIncome + ", " : "") +
            (costOfInvestment != null ? "costOfInvestment=" + costOfInvestment + ", " : "") +
            (demandedLandAreaInHector != null ? "demandedLandAreaInHector=" + demandedLandAreaInHector + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (seasonOfCropLoan != null ? "seasonOfCropLoan=" + seasonOfCropLoan + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (societyLoanProductId != null ? "societyLoanProductId=" + societyLoanProductId + ", " : "") +
            (memberLandAssetsId != null ? "memberLandAssetsId=" + memberLandAssetsId + ", " : "") +
            (societyCropRegistrationId != null ? "societyCropRegistrationId=" + societyCropRegistrationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
