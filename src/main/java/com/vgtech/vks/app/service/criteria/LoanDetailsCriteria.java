package com.vgtech.vks.app.service.criteria;

import com.vgtech.vks.app.domain.enumeration.LoanStatus;
import com.vgtech.vks.app.domain.enumeration.LoanType;
import com.vgtech.vks.app.domain.enumeration.NpaClassification;
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
 * Criteria class for the {@link com.vgtech.vks.app.domain.LoanDetails} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.LoanDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /loan-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class LoanDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering LoanType
     */
    public static class LoanTypeFilter extends Filter<LoanType> {

        public LoanTypeFilter() {}

        public LoanTypeFilter(LoanTypeFilter filter) {
            super(filter);
        }

        @Override
        public LoanTypeFilter copy() {
            return new LoanTypeFilter(this);
        }
    }

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

    /**
     * Class for filtering NpaClassification
     */
    public static class NpaClassificationFilter extends Filter<NpaClassification> {

        public NpaClassificationFilter() {}

        public NpaClassificationFilter(NpaClassificationFilter filter) {
            super(filter);
        }

        @Override
        public NpaClassificationFilter copy() {
            return new NpaClassificationFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter loanAmount;

    private StringFilter loanAccountNo;

    private LoanTypeFilter loanType;

    private LoanStatusFilter status;

    private InstantFilter loanStartDate;

    private InstantFilter loanEndDate;

    private InstantFilter loanPlannedClosureDate;

    private InstantFilter loanCloserDate;

    private InstantFilter loanEffectiveDate;

    private NpaClassificationFilter loanClassification;

    private StringFilter resolutionNo;

    private InstantFilter resolutionDate;

    private DoubleFilter costOfInvestment;

    private DoubleFilter loanBenefitingArea;

    private LongFilter dccbLoanNo;

    private LongFilter mortgageDeedNo;

    private InstantFilter mortgageDate;

    private DoubleFilter extentMorgageValue;

    private StringFilter parentAccHeadCode;

    private StringFilter loanAccountName;

    private DoubleFilter disbursementAmt;

    private StringFilter disbursementStatus;

    private StringFilter year;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private LongFilter loanDemandId;

    private LongFilter memberId;

    private LongFilter loanDemandId;

    private LongFilter societyLoanProductId;

    private LongFilter bankDhoranDetailsId;

    private Boolean distinct;

    public LoanDetailsCriteria() {}

    public LoanDetailsCriteria(LoanDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.loanAmount = other.loanAmount == null ? null : other.loanAmount.copy();
        this.loanAccountNo = other.loanAccountNo == null ? null : other.loanAccountNo.copy();
        this.loanType = other.loanType == null ? null : other.loanType.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.loanStartDate = other.loanStartDate == null ? null : other.loanStartDate.copy();
        this.loanEndDate = other.loanEndDate == null ? null : other.loanEndDate.copy();
        this.loanPlannedClosureDate = other.loanPlannedClosureDate == null ? null : other.loanPlannedClosureDate.copy();
        this.loanCloserDate = other.loanCloserDate == null ? null : other.loanCloserDate.copy();
        this.loanEffectiveDate = other.loanEffectiveDate == null ? null : other.loanEffectiveDate.copy();
        this.loanClassification = other.loanClassification == null ? null : other.loanClassification.copy();
        this.resolutionNo = other.resolutionNo == null ? null : other.resolutionNo.copy();
        this.resolutionDate = other.resolutionDate == null ? null : other.resolutionDate.copy();
        this.costOfInvestment = other.costOfInvestment == null ? null : other.costOfInvestment.copy();
        this.loanBenefitingArea = other.loanBenefitingArea == null ? null : other.loanBenefitingArea.copy();
        this.dccbLoanNo = other.dccbLoanNo == null ? null : other.dccbLoanNo.copy();
        this.mortgageDeedNo = other.mortgageDeedNo == null ? null : other.mortgageDeedNo.copy();
        this.mortgageDate = other.mortgageDate == null ? null : other.mortgageDate.copy();
        this.extentMorgageValue = other.extentMorgageValue == null ? null : other.extentMorgageValue.copy();
        this.parentAccHeadCode = other.parentAccHeadCode == null ? null : other.parentAccHeadCode.copy();
        this.loanAccountName = other.loanAccountName == null ? null : other.loanAccountName.copy();
        this.disbursementAmt = other.disbursementAmt == null ? null : other.disbursementAmt.copy();
        this.disbursementStatus = other.disbursementStatus == null ? null : other.disbursementStatus.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.loanDemandId = other.loanDemandId == null ? null : other.loanDemandId.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.loanDemandId = other.loanDemandId == null ? null : other.loanDemandId.copy();
        this.societyLoanProductId = other.societyLoanProductId == null ? null : other.societyLoanProductId.copy();
        this.bankDhoranDetailsId = other.bankDhoranDetailsId == null ? null : other.bankDhoranDetailsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LoanDetailsCriteria copy() {
        return new LoanDetailsCriteria(this);
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

    public DoubleFilter getLoanAmount() {
        return loanAmount;
    }

    public DoubleFilter loanAmount() {
        if (loanAmount == null) {
            loanAmount = new DoubleFilter();
        }
        return loanAmount;
    }

    public void setLoanAmount(DoubleFilter loanAmount) {
        this.loanAmount = loanAmount;
    }

    public StringFilter getLoanAccountNo() {
        return loanAccountNo;
    }

    public StringFilter loanAccountNo() {
        if (loanAccountNo == null) {
            loanAccountNo = new StringFilter();
        }
        return loanAccountNo;
    }

    public void setLoanAccountNo(StringFilter loanAccountNo) {
        this.loanAccountNo = loanAccountNo;
    }

    public LoanTypeFilter getLoanType() {
        return loanType;
    }

    public LoanTypeFilter loanType() {
        if (loanType == null) {
            loanType = new LoanTypeFilter();
        }
        return loanType;
    }

    public void setLoanType(LoanTypeFilter loanType) {
        this.loanType = loanType;
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

    public InstantFilter getLoanStartDate() {
        return loanStartDate;
    }

    public InstantFilter loanStartDate() {
        if (loanStartDate == null) {
            loanStartDate = new InstantFilter();
        }
        return loanStartDate;
    }

    public void setLoanStartDate(InstantFilter loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public InstantFilter getLoanEndDate() {
        return loanEndDate;
    }

    public InstantFilter loanEndDate() {
        if (loanEndDate == null) {
            loanEndDate = new InstantFilter();
        }
        return loanEndDate;
    }

    public void setLoanEndDate(InstantFilter loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public InstantFilter getLoanPlannedClosureDate() {
        return loanPlannedClosureDate;
    }

    public InstantFilter loanPlannedClosureDate() {
        if (loanPlannedClosureDate == null) {
            loanPlannedClosureDate = new InstantFilter();
        }
        return loanPlannedClosureDate;
    }

    public void setLoanPlannedClosureDate(InstantFilter loanPlannedClosureDate) {
        this.loanPlannedClosureDate = loanPlannedClosureDate;
    }

    public InstantFilter getLoanCloserDate() {
        return loanCloserDate;
    }

    public InstantFilter loanCloserDate() {
        if (loanCloserDate == null) {
            loanCloserDate = new InstantFilter();
        }
        return loanCloserDate;
    }

    public void setLoanCloserDate(InstantFilter loanCloserDate) {
        this.loanCloserDate = loanCloserDate;
    }

    public InstantFilter getLoanEffectiveDate() {
        return loanEffectiveDate;
    }

    public InstantFilter loanEffectiveDate() {
        if (loanEffectiveDate == null) {
            loanEffectiveDate = new InstantFilter();
        }
        return loanEffectiveDate;
    }

    public void setLoanEffectiveDate(InstantFilter loanEffectiveDate) {
        this.loanEffectiveDate = loanEffectiveDate;
    }

    public NpaClassificationFilter getLoanClassification() {
        return loanClassification;
    }

    public NpaClassificationFilter loanClassification() {
        if (loanClassification == null) {
            loanClassification = new NpaClassificationFilter();
        }
        return loanClassification;
    }

    public void setLoanClassification(NpaClassificationFilter loanClassification) {
        this.loanClassification = loanClassification;
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

    public DoubleFilter getLoanBenefitingArea() {
        return loanBenefitingArea;
    }

    public DoubleFilter loanBenefitingArea() {
        if (loanBenefitingArea == null) {
            loanBenefitingArea = new DoubleFilter();
        }
        return loanBenefitingArea;
    }

    public void setLoanBenefitingArea(DoubleFilter loanBenefitingArea) {
        this.loanBenefitingArea = loanBenefitingArea;
    }

    public LongFilter getDccbLoanNo() {
        return dccbLoanNo;
    }

    public LongFilter dccbLoanNo() {
        if (dccbLoanNo == null) {
            dccbLoanNo = new LongFilter();
        }
        return dccbLoanNo;
    }

    public void setDccbLoanNo(LongFilter dccbLoanNo) {
        this.dccbLoanNo = dccbLoanNo;
    }

    public LongFilter getMortgageDeedNo() {
        return mortgageDeedNo;
    }

    public LongFilter mortgageDeedNo() {
        if (mortgageDeedNo == null) {
            mortgageDeedNo = new LongFilter();
        }
        return mortgageDeedNo;
    }

    public void setMortgageDeedNo(LongFilter mortgageDeedNo) {
        this.mortgageDeedNo = mortgageDeedNo;
    }

    public InstantFilter getMortgageDate() {
        return mortgageDate;
    }

    public InstantFilter mortgageDate() {
        if (mortgageDate == null) {
            mortgageDate = new InstantFilter();
        }
        return mortgageDate;
    }

    public void setMortgageDate(InstantFilter mortgageDate) {
        this.mortgageDate = mortgageDate;
    }

    public DoubleFilter getExtentMorgageValue() {
        return extentMorgageValue;
    }

    public DoubleFilter extentMorgageValue() {
        if (extentMorgageValue == null) {
            extentMorgageValue = new DoubleFilter();
        }
        return extentMorgageValue;
    }

    public void setExtentMorgageValue(DoubleFilter extentMorgageValue) {
        this.extentMorgageValue = extentMorgageValue;
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

    public StringFilter getLoanAccountName() {
        return loanAccountName;
    }

    public StringFilter loanAccountName() {
        if (loanAccountName == null) {
            loanAccountName = new StringFilter();
        }
        return loanAccountName;
    }

    public void setLoanAccountName(StringFilter loanAccountName) {
        this.loanAccountName = loanAccountName;
    }

    public DoubleFilter getDisbursementAmt() {
        return disbursementAmt;
    }

    public DoubleFilter disbursementAmt() {
        if (disbursementAmt == null) {
            disbursementAmt = new DoubleFilter();
        }
        return disbursementAmt;
    }

    public void setDisbursementAmt(DoubleFilter disbursementAmt) {
        this.disbursementAmt = disbursementAmt;
    }

    public StringFilter getDisbursementStatus() {
        return disbursementStatus;
    }

    public StringFilter disbursementStatus() {
        if (disbursementStatus == null) {
            disbursementStatus = new StringFilter();
        }
        return disbursementStatus;
    }

    public void setDisbursementStatus(StringFilter disbursementStatus) {
        this.disbursementStatus = disbursementStatus;
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

    public LongFilter getLoanDemandId() {
        return loanDemandId;
    }

    public LongFilter loanDemandId() {
        if (loanDemandId == null) {
            loanDemandId = new LongFilter();
        }
        return loanDemandId;
    }

    public void setLoanDemandId(LongFilter loanDemandId) {
        this.loanDemandId = loanDemandId;
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

    public LongFilter getLoanDemandId() {
        return loanDemandId;
    }

    public LongFilter loanDemandId() {
        if (loanDemandId == null) {
            loanDemandId = new LongFilter();
        }
        return loanDemandId;
    }

    public void setLoanDemandId(LongFilter loanDemandId) {
        this.loanDemandId = loanDemandId;
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
        final LoanDetailsCriteria that = (LoanDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(loanAmount, that.loanAmount) &&
            Objects.equals(loanAccountNo, that.loanAccountNo) &&
            Objects.equals(loanType, that.loanType) &&
            Objects.equals(status, that.status) &&
            Objects.equals(loanStartDate, that.loanStartDate) &&
            Objects.equals(loanEndDate, that.loanEndDate) &&
            Objects.equals(loanPlannedClosureDate, that.loanPlannedClosureDate) &&
            Objects.equals(loanCloserDate, that.loanCloserDate) &&
            Objects.equals(loanEffectiveDate, that.loanEffectiveDate) &&
            Objects.equals(loanClassification, that.loanClassification) &&
            Objects.equals(resolutionNo, that.resolutionNo) &&
            Objects.equals(resolutionDate, that.resolutionDate) &&
            Objects.equals(costOfInvestment, that.costOfInvestment) &&
            Objects.equals(loanBenefitingArea, that.loanBenefitingArea) &&
            Objects.equals(dccbLoanNo, that.dccbLoanNo) &&
            Objects.equals(mortgageDeedNo, that.mortgageDeedNo) &&
            Objects.equals(mortgageDate, that.mortgageDate) &&
            Objects.equals(extentMorgageValue, that.extentMorgageValue) &&
            Objects.equals(parentAccHeadCode, that.parentAccHeadCode) &&
            Objects.equals(loanAccountName, that.loanAccountName) &&
            Objects.equals(disbursementAmt, that.disbursementAmt) &&
            Objects.equals(disbursementStatus, that.disbursementStatus) &&
            Objects.equals(year, that.year) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(loanDemandId, that.loanDemandId) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(loanDemandId, that.loanDemandId) &&
            Objects.equals(societyLoanProductId, that.societyLoanProductId) &&
            Objects.equals(bankDhoranDetailsId, that.bankDhoranDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            loanAmount,
            loanAccountNo,
            loanType,
            status,
            loanStartDate,
            loanEndDate,
            loanPlannedClosureDate,
            loanCloserDate,
            loanEffectiveDate,
            loanClassification,
            resolutionNo,
            resolutionDate,
            costOfInvestment,
            loanBenefitingArea,
            dccbLoanNo,
            mortgageDeedNo,
            mortgageDate,
            extentMorgageValue,
            parentAccHeadCode,
            loanAccountName,
            disbursementAmt,
            disbursementStatus,
            year,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            loanDemandId,
            memberId,
            loanDemandId,
            societyLoanProductId,
            bankDhoranDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (loanAmount != null ? "loanAmount=" + loanAmount + ", " : "") +
            (loanAccountNo != null ? "loanAccountNo=" + loanAccountNo + ", " : "") +
            (loanType != null ? "loanType=" + loanType + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (loanStartDate != null ? "loanStartDate=" + loanStartDate + ", " : "") +
            (loanEndDate != null ? "loanEndDate=" + loanEndDate + ", " : "") +
            (loanPlannedClosureDate != null ? "loanPlannedClosureDate=" + loanPlannedClosureDate + ", " : "") +
            (loanCloserDate != null ? "loanCloserDate=" + loanCloserDate + ", " : "") +
            (loanEffectiveDate != null ? "loanEffectiveDate=" + loanEffectiveDate + ", " : "") +
            (loanClassification != null ? "loanClassification=" + loanClassification + ", " : "") +
            (resolutionNo != null ? "resolutionNo=" + resolutionNo + ", " : "") +
            (resolutionDate != null ? "resolutionDate=" + resolutionDate + ", " : "") +
            (costOfInvestment != null ? "costOfInvestment=" + costOfInvestment + ", " : "") +
            (loanBenefitingArea != null ? "loanBenefitingArea=" + loanBenefitingArea + ", " : "") +
            (dccbLoanNo != null ? "dccbLoanNo=" + dccbLoanNo + ", " : "") +
            (mortgageDeedNo != null ? "mortgageDeedNo=" + mortgageDeedNo + ", " : "") +
            (mortgageDate != null ? "mortgageDate=" + mortgageDate + ", " : "") +
            (extentMorgageValue != null ? "extentMorgageValue=" + extentMorgageValue + ", " : "") +
            (parentAccHeadCode != null ? "parentAccHeadCode=" + parentAccHeadCode + ", " : "") +
            (loanAccountName != null ? "loanAccountName=" + loanAccountName + ", " : "") +
            (disbursementAmt != null ? "disbursementAmt=" + disbursementAmt + ", " : "") +
            (disbursementStatus != null ? "disbursementStatus=" + disbursementStatus + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (loanDemandId != null ? "loanDemandId=" + loanDemandId + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (loanDemandId != null ? "loanDemandId=" + loanDemandId + ", " : "") +
            (societyLoanProductId != null ? "societyLoanProductId=" + societyLoanProductId + ", " : "") +
            (bankDhoranDetailsId != null ? "bankDhoranDetailsId=" + bankDhoranDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
