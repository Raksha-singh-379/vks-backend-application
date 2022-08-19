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
 * Criteria class for the {@link com.vgtech.vks.app.domain.VoucherDetails} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.VoucherDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /voucher-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class VoucherDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter accHeadCode;

    private StringFilter creditAccount;

    private StringFilter debitAccount;

    private DoubleFilter transferAmt;

    private BooleanFilter isDeleted;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private Boolean distinct;

    public VoucherDetailsCriteria() {}

    public VoucherDetailsCriteria(VoucherDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.accHeadCode = other.accHeadCode == null ? null : other.accHeadCode.copy();
        this.creditAccount = other.creditAccount == null ? null : other.creditAccount.copy();
        this.debitAccount = other.debitAccount == null ? null : other.debitAccount.copy();
        this.transferAmt = other.transferAmt == null ? null : other.transferAmt.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VoucherDetailsCriteria copy() {
        return new VoucherDetailsCriteria(this);
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

    public StringFilter getCreditAccount() {
        return creditAccount;
    }

    public StringFilter creditAccount() {
        if (creditAccount == null) {
            creditAccount = new StringFilter();
        }
        return creditAccount;
    }

    public void setCreditAccount(StringFilter creditAccount) {
        this.creditAccount = creditAccount;
    }

    public StringFilter getDebitAccount() {
        return debitAccount;
    }

    public StringFilter debitAccount() {
        if (debitAccount == null) {
            debitAccount = new StringFilter();
        }
        return debitAccount;
    }

    public void setDebitAccount(StringFilter debitAccount) {
        this.debitAccount = debitAccount;
    }

    public DoubleFilter getTransferAmt() {
        return transferAmt;
    }

    public DoubleFilter transferAmt() {
        if (transferAmt == null) {
            transferAmt = new DoubleFilter();
        }
        return transferAmt;
    }

    public void setTransferAmt(DoubleFilter transferAmt) {
        this.transferAmt = transferAmt;
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
        final VoucherDetailsCriteria that = (VoucherDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(accHeadCode, that.accHeadCode) &&
            Objects.equals(creditAccount, that.creditAccount) &&
            Objects.equals(debitAccount, that.debitAccount) &&
            Objects.equals(transferAmt, that.transferAmt) &&
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
            accHeadCode,
            creditAccount,
            debitAccount,
            transferAmt,
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
        return "VoucherDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (accHeadCode != null ? "accHeadCode=" + accHeadCode + ", " : "") +
            (creditAccount != null ? "creditAccount=" + creditAccount + ", " : "") +
            (debitAccount != null ? "debitAccount=" + debitAccount + ", " : "") +
            (transferAmt != null ? "transferAmt=" + transferAmt + ", " : "") +
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
