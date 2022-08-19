package com.vgtech.vks.app.service.criteria;

import com.vgtech.vks.app.domain.enumeration.PaymentMode;
import com.vgtech.vks.app.domain.enumeration.VoucherCode;
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
 * Criteria class for the {@link com.vgtech.vks.app.domain.Vouchers} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.VouchersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vouchers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class VouchersCriteria implements Serializable, Criteria {

    /**
     * Class for filtering VoucherCode
     */
    public static class VoucherCodeFilter extends Filter<VoucherCode> {

        public VoucherCodeFilter() {}

        public VoucherCodeFilter(VoucherCodeFilter filter) {
            super(filter);
        }

        @Override
        public VoucherCodeFilter copy() {
            return new VoucherCodeFilter(this);
        }
    }

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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter voucherDate;

    private StringFilter voucherNo;

    private StringFilter preparedBy;

    private VoucherCodeFilter code;

    private StringFilter narration;

    private StringFilter authorisedBy;

    private PaymentModeFilter mode;

    private BooleanFilter isDeleted;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private Boolean distinct;

    public VouchersCriteria() {}

    public VouchersCriteria(VouchersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.voucherDate = other.voucherDate == null ? null : other.voucherDate.copy();
        this.voucherNo = other.voucherNo == null ? null : other.voucherNo.copy();
        this.preparedBy = other.preparedBy == null ? null : other.preparedBy.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.narration = other.narration == null ? null : other.narration.copy();
        this.authorisedBy = other.authorisedBy == null ? null : other.authorisedBy.copy();
        this.mode = other.mode == null ? null : other.mode.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VouchersCriteria copy() {
        return new VouchersCriteria(this);
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

    public InstantFilter getVoucherDate() {
        return voucherDate;
    }

    public InstantFilter voucherDate() {
        if (voucherDate == null) {
            voucherDate = new InstantFilter();
        }
        return voucherDate;
    }

    public void setVoucherDate(InstantFilter voucherDate) {
        this.voucherDate = voucherDate;
    }

    public StringFilter getVoucherNo() {
        return voucherNo;
    }

    public StringFilter voucherNo() {
        if (voucherNo == null) {
            voucherNo = new StringFilter();
        }
        return voucherNo;
    }

    public void setVoucherNo(StringFilter voucherNo) {
        this.voucherNo = voucherNo;
    }

    public StringFilter getPreparedBy() {
        return preparedBy;
    }

    public StringFilter preparedBy() {
        if (preparedBy == null) {
            preparedBy = new StringFilter();
        }
        return preparedBy;
    }

    public void setPreparedBy(StringFilter preparedBy) {
        this.preparedBy = preparedBy;
    }

    public VoucherCodeFilter getCode() {
        return code;
    }

    public VoucherCodeFilter code() {
        if (code == null) {
            code = new VoucherCodeFilter();
        }
        return code;
    }

    public void setCode(VoucherCodeFilter code) {
        this.code = code;
    }

    public StringFilter getNarration() {
        return narration;
    }

    public StringFilter narration() {
        if (narration == null) {
            narration = new StringFilter();
        }
        return narration;
    }

    public void setNarration(StringFilter narration) {
        this.narration = narration;
    }

    public StringFilter getAuthorisedBy() {
        return authorisedBy;
    }

    public StringFilter authorisedBy() {
        if (authorisedBy == null) {
            authorisedBy = new StringFilter();
        }
        return authorisedBy;
    }

    public void setAuthorisedBy(StringFilter authorisedBy) {
        this.authorisedBy = authorisedBy;
    }

    public PaymentModeFilter getMode() {
        return mode;
    }

    public PaymentModeFilter mode() {
        if (mode == null) {
            mode = new PaymentModeFilter();
        }
        return mode;
    }

    public void setMode(PaymentModeFilter mode) {
        this.mode = mode;
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
        final VouchersCriteria that = (VouchersCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(voucherDate, that.voucherDate) &&
            Objects.equals(voucherNo, that.voucherNo) &&
            Objects.equals(preparedBy, that.preparedBy) &&
            Objects.equals(code, that.code) &&
            Objects.equals(narration, that.narration) &&
            Objects.equals(authorisedBy, that.authorisedBy) &&
            Objects.equals(mode, that.mode) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            voucherDate,
            voucherNo,
            preparedBy,
            code,
            narration,
            authorisedBy,
            mode,
            isDeleted,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VouchersCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (voucherDate != null ? "voucherDate=" + voucherDate + ", " : "") +
            (voucherNo != null ? "voucherNo=" + voucherNo + ", " : "") +
            (preparedBy != null ? "preparedBy=" + preparedBy + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (narration != null ? "narration=" + narration + ", " : "") +
            (authorisedBy != null ? "authorisedBy=" + authorisedBy + ", " : "") +
            (mode != null ? "mode=" + mode + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
