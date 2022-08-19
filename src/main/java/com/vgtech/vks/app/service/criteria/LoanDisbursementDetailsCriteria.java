package com.vgtech.vks.app.service.criteria;

import com.vgtech.vks.app.domain.enumeration.LoanType;
import com.vgtech.vks.app.domain.enumeration.PaymentMode;
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
 * Criteria class for the {@link com.vgtech.vks.app.domain.LoanDisbursementDetails} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.LoanDisbursementDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /loan-disbursement-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class LoanDisbursementDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering PaymentMode
     */
    public static class PaymentModeFilter extends Filter<PaymentMode> {

        public PaymentModeFilter() {}

        public PaymentModeFilter(PaymentModeFilter filter) {
            super(filter);
        }

        @Override
        public PaymentModeFilter copy() {
            return new PaymentModeFilter(this);
        }
    }

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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter disbursementDate;

    private DoubleFilter disbursementAmount;

    private IntegerFilter disbursementNumber;

    private StringFilter disbursementStatus;

    private PaymentModeFilter paymentMode;

    private LoanTypeFilter type;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private LongFilter loanDetailsId;

    private Boolean distinct;

    public LoanDisbursementDetailsCriteria() {}

    public LoanDisbursementDetailsCriteria(LoanDisbursementDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.disbursementDate = other.disbursementDate == null ? null : other.disbursementDate.copy();
        this.disbursementAmount = other.disbursementAmount == null ? null : other.disbursementAmount.copy();
        this.disbursementNumber = other.disbursementNumber == null ? null : other.disbursementNumber.copy();
        this.disbursementStatus = other.disbursementStatus == null ? null : other.disbursementStatus.copy();
        this.paymentMode = other.paymentMode == null ? null : other.paymentMode.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.loanDetailsId = other.loanDetailsId == null ? null : other.loanDetailsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LoanDisbursementDetailsCriteria copy() {
        return new LoanDisbursementDetailsCriteria(this);
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

    public InstantFilter getDisbursementDate() {
        return disbursementDate;
    }

    public InstantFilter disbursementDate() {
        if (disbursementDate == null) {
            disbursementDate = new InstantFilter();
        }
        return disbursementDate;
    }

    public void setDisbursementDate(InstantFilter disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public DoubleFilter getDisbursementAmount() {
        return disbursementAmount;
    }

    public DoubleFilter disbursementAmount() {
        if (disbursementAmount == null) {
            disbursementAmount = new DoubleFilter();
        }
        return disbursementAmount;
    }

    public void setDisbursementAmount(DoubleFilter disbursementAmount) {
        this.disbursementAmount = disbursementAmount;
    }

    public IntegerFilter getDisbursementNumber() {
        return disbursementNumber;
    }

    public IntegerFilter disbursementNumber() {
        if (disbursementNumber == null) {
            disbursementNumber = new IntegerFilter();
        }
        return disbursementNumber;
    }

    public void setDisbursementNumber(IntegerFilter disbursementNumber) {
        this.disbursementNumber = disbursementNumber;
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

    public PaymentModeFilter getPaymentMode() {
        return paymentMode;
    }

    public PaymentModeFilter paymentMode() {
        if (paymentMode == null) {
            paymentMode = new PaymentModeFilter();
        }
        return paymentMode;
    }

    public void setPaymentMode(PaymentModeFilter paymentMode) {
        this.paymentMode = paymentMode;
    }

    public LoanTypeFilter getType() {
        return type;
    }

    public LoanTypeFilter type() {
        if (type == null) {
            type = new LoanTypeFilter();
        }
        return type;
    }

    public void setType(LoanTypeFilter type) {
        this.type = type;
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

    public LongFilter getLoanDetailsId() {
        return loanDetailsId;
    }

    public LongFilter loanDetailsId() {
        if (loanDetailsId == null) {
            loanDetailsId = new LongFilter();
        }
        return loanDetailsId;
    }

    public void setLoanDetailsId(LongFilter loanDetailsId) {
        this.loanDetailsId = loanDetailsId;
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
        final LoanDisbursementDetailsCriteria that = (LoanDisbursementDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(disbursementDate, that.disbursementDate) &&
            Objects.equals(disbursementAmount, that.disbursementAmount) &&
            Objects.equals(disbursementNumber, that.disbursementNumber) &&
            Objects.equals(disbursementStatus, that.disbursementStatus) &&
            Objects.equals(paymentMode, that.paymentMode) &&
            Objects.equals(type, that.type) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(loanDetailsId, that.loanDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            disbursementDate,
            disbursementAmount,
            disbursementNumber,
            disbursementStatus,
            paymentMode,
            type,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            loanDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanDisbursementDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (disbursementDate != null ? "disbursementDate=" + disbursementDate + ", " : "") +
            (disbursementAmount != null ? "disbursementAmount=" + disbursementAmount + ", " : "") +
            (disbursementNumber != null ? "disbursementNumber=" + disbursementNumber + ", " : "") +
            (disbursementStatus != null ? "disbursementStatus=" + disbursementStatus + ", " : "") +
            (paymentMode != null ? "paymentMode=" + paymentMode + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (loanDetailsId != null ? "loanDetailsId=" + loanDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
