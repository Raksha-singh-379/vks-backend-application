package com.vgtech.vks.app.service.criteria;

import com.vgtech.vks.app.domain.enumeration.LoanType;
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
 * Criteria class for the {@link com.vgtech.vks.app.domain.SocietyPrerequisite} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.SocietyPrerequisiteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /society-prerequisites?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SocietyPrerequisiteCriteria implements Serializable, Criteria {

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

    private StringFilter docType;

    private StringFilter documentDesc;

    private StringFilter documentName;

    private LoanTypeFilter loanType;

    private StringFilter mandatory;

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

    public SocietyPrerequisiteCriteria() {}

    public SocietyPrerequisiteCriteria(SocietyPrerequisiteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.docType = other.docType == null ? null : other.docType.copy();
        this.documentDesc = other.documentDesc == null ? null : other.documentDesc.copy();
        this.documentName = other.documentName == null ? null : other.documentName.copy();
        this.loanType = other.loanType == null ? null : other.loanType.copy();
        this.mandatory = other.mandatory == null ? null : other.mandatory.copy();
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
    public SocietyPrerequisiteCriteria copy() {
        return new SocietyPrerequisiteCriteria(this);
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

    public StringFilter getDocType() {
        return docType;
    }

    public StringFilter docType() {
        if (docType == null) {
            docType = new StringFilter();
        }
        return docType;
    }

    public void setDocType(StringFilter docType) {
        this.docType = docType;
    }

    public StringFilter getDocumentDesc() {
        return documentDesc;
    }

    public StringFilter documentDesc() {
        if (documentDesc == null) {
            documentDesc = new StringFilter();
        }
        return documentDesc;
    }

    public void setDocumentDesc(StringFilter documentDesc) {
        this.documentDesc = documentDesc;
    }

    public StringFilter getDocumentName() {
        return documentName;
    }

    public StringFilter documentName() {
        if (documentName == null) {
            documentName = new StringFilter();
        }
        return documentName;
    }

    public void setDocumentName(StringFilter documentName) {
        this.documentName = documentName;
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

    public StringFilter getMandatory() {
        return mandatory;
    }

    public StringFilter mandatory() {
        if (mandatory == null) {
            mandatory = new StringFilter();
        }
        return mandatory;
    }

    public void setMandatory(StringFilter mandatory) {
        this.mandatory = mandatory;
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
        final SocietyPrerequisiteCriteria that = (SocietyPrerequisiteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(docType, that.docType) &&
            Objects.equals(documentDesc, that.documentDesc) &&
            Objects.equals(documentName, that.documentName) &&
            Objects.equals(loanType, that.loanType) &&
            Objects.equals(mandatory, that.mandatory) &&
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
            docType,
            documentDesc,
            documentName,
            loanType,
            mandatory,
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
        return "SocietyPrerequisiteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (docType != null ? "docType=" + docType + ", " : "") +
            (documentDesc != null ? "documentDesc=" + documentDesc + ", " : "") +
            (documentName != null ? "documentName=" + documentName + ", " : "") +
            (loanType != null ? "loanType=" + loanType + ", " : "") +
            (mandatory != null ? "mandatory=" + mandatory + ", " : "") +
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
