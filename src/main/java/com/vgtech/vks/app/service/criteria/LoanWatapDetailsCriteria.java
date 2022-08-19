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
 * Criteria class for the {@link com.vgtech.vks.app.domain.LoanWatapDetails} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.LoanWatapDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /loan-watap-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class LoanWatapDetailsCriteria implements Serializable, Criteria {

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

    private InstantFilter loanWatapDate;

    private DoubleFilter cropLandInHector;

    private IntegerFilter slotNumber;

    private DoubleFilter loanAmount;

    private StringFilter season;

    private LoanStatusFilter status;

    private StringFilter year;

    private BooleanFilter isDeleted;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private LongFilter loanDemandId;

    private Boolean distinct;

    public LoanWatapDetailsCriteria() {}

    public LoanWatapDetailsCriteria(LoanWatapDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.loanWatapDate = other.loanWatapDate == null ? null : other.loanWatapDate.copy();
        this.cropLandInHector = other.cropLandInHector == null ? null : other.cropLandInHector.copy();
        this.slotNumber = other.slotNumber == null ? null : other.slotNumber.copy();
        this.loanAmount = other.loanAmount == null ? null : other.loanAmount.copy();
        this.season = other.season == null ? null : other.season.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.loanDemandId = other.loanDemandId == null ? null : other.loanDemandId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LoanWatapDetailsCriteria copy() {
        return new LoanWatapDetailsCriteria(this);
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

    public InstantFilter getLoanWatapDate() {
        return loanWatapDate;
    }

    public InstantFilter loanWatapDate() {
        if (loanWatapDate == null) {
            loanWatapDate = new InstantFilter();
        }
        return loanWatapDate;
    }

    public void setLoanWatapDate(InstantFilter loanWatapDate) {
        this.loanWatapDate = loanWatapDate;
    }

    public DoubleFilter getCropLandInHector() {
        return cropLandInHector;
    }

    public DoubleFilter cropLandInHector() {
        if (cropLandInHector == null) {
            cropLandInHector = new DoubleFilter();
        }
        return cropLandInHector;
    }

    public void setCropLandInHector(DoubleFilter cropLandInHector) {
        this.cropLandInHector = cropLandInHector;
    }

    public IntegerFilter getSlotNumber() {
        return slotNumber;
    }

    public IntegerFilter slotNumber() {
        if (slotNumber == null) {
            slotNumber = new IntegerFilter();
        }
        return slotNumber;
    }

    public void setSlotNumber(IntegerFilter slotNumber) {
        this.slotNumber = slotNumber;
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

    public StringFilter getSeason() {
        return season;
    }

    public StringFilter season() {
        if (season == null) {
            season = new StringFilter();
        }
        return season;
    }

    public void setSeason(StringFilter season) {
        this.season = season;
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
        final LoanWatapDetailsCriteria that = (LoanWatapDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(loanWatapDate, that.loanWatapDate) &&
            Objects.equals(cropLandInHector, that.cropLandInHector) &&
            Objects.equals(slotNumber, that.slotNumber) &&
            Objects.equals(loanAmount, that.loanAmount) &&
            Objects.equals(season, that.season) &&
            Objects.equals(status, that.status) &&
            Objects.equals(year, that.year) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(loanDemandId, that.loanDemandId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            loanWatapDate,
            cropLandInHector,
            slotNumber,
            loanAmount,
            season,
            status,
            year,
            isDeleted,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            loanDemandId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanWatapDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (loanWatapDate != null ? "loanWatapDate=" + loanWatapDate + ", " : "") +
            (cropLandInHector != null ? "cropLandInHector=" + cropLandInHector + ", " : "") +
            (slotNumber != null ? "slotNumber=" + slotNumber + ", " : "") +
            (loanAmount != null ? "loanAmount=" + loanAmount + ", " : "") +
            (season != null ? "season=" + season + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (loanDemandId != null ? "loanDemandId=" + loanDemandId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
