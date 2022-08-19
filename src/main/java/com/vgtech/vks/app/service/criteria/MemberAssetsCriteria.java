package com.vgtech.vks.app.service.criteria;

import com.vgtech.vks.app.domain.enumeration.AssetType;
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
 * Criteria class for the {@link com.vgtech.vks.app.domain.MemberAssets} entity. This class is used
 * in {@link com.vgtech.vks.app.web.rest.MemberAssetsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /member-assets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class MemberAssetsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering AssetType
     */
    public static class AssetTypeFilter extends Filter<AssetType> {

        public AssetTypeFilter() {}

        public AssetTypeFilter(AssetTypeFilter filter) {
            super(filter);
        }

        @Override
        public AssetTypeFilter copy() {
            return new AssetTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter assetAmount;

    private AssetTypeFilter assetType;

    private IntegerFilter assetArea;

    private StringFilter assetAddress;

    private IntegerFilter numberOfAssets;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter isDeleted;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private LongFilter memberId;

    private Boolean distinct;

    public MemberAssetsCriteria() {}

    public MemberAssetsCriteria(MemberAssetsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.assetAmount = other.assetAmount == null ? null : other.assetAmount.copy();
        this.assetType = other.assetType == null ? null : other.assetType.copy();
        this.assetArea = other.assetArea == null ? null : other.assetArea.copy();
        this.assetAddress = other.assetAddress == null ? null : other.assetAddress.copy();
        this.numberOfAssets = other.numberOfAssets == null ? null : other.numberOfAssets.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MemberAssetsCriteria copy() {
        return new MemberAssetsCriteria(this);
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

    public DoubleFilter getAssetAmount() {
        return assetAmount;
    }

    public DoubleFilter assetAmount() {
        if (assetAmount == null) {
            assetAmount = new DoubleFilter();
        }
        return assetAmount;
    }

    public void setAssetAmount(DoubleFilter assetAmount) {
        this.assetAmount = assetAmount;
    }

    public AssetTypeFilter getAssetType() {
        return assetType;
    }

    public AssetTypeFilter assetType() {
        if (assetType == null) {
            assetType = new AssetTypeFilter();
        }
        return assetType;
    }

    public void setAssetType(AssetTypeFilter assetType) {
        this.assetType = assetType;
    }

    public IntegerFilter getAssetArea() {
        return assetArea;
    }

    public IntegerFilter assetArea() {
        if (assetArea == null) {
            assetArea = new IntegerFilter();
        }
        return assetArea;
    }

    public void setAssetArea(IntegerFilter assetArea) {
        this.assetArea = assetArea;
    }

    public StringFilter getAssetAddress() {
        return assetAddress;
    }

    public StringFilter assetAddress() {
        if (assetAddress == null) {
            assetAddress = new StringFilter();
        }
        return assetAddress;
    }

    public void setAssetAddress(StringFilter assetAddress) {
        this.assetAddress = assetAddress;
    }

    public IntegerFilter getNumberOfAssets() {
        return numberOfAssets;
    }

    public IntegerFilter numberOfAssets() {
        if (numberOfAssets == null) {
            numberOfAssets = new IntegerFilter();
        }
        return numberOfAssets;
    }

    public void setNumberOfAssets(IntegerFilter numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
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
        final MemberAssetsCriteria that = (MemberAssetsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(assetAmount, that.assetAmount) &&
            Objects.equals(assetType, that.assetType) &&
            Objects.equals(assetArea, that.assetArea) &&
            Objects.equals(assetAddress, that.assetAddress) &&
            Objects.equals(numberOfAssets, that.numberOfAssets) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            assetAmount,
            assetType,
            assetArea,
            assetAddress,
            numberOfAssets,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            isDeleted,
            freeField1,
            freeField2,
            freeField3,
            memberId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberAssetsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (assetAmount != null ? "assetAmount=" + assetAmount + ", " : "") +
            (assetType != null ? "assetType=" + assetType + ", " : "") +
            (assetArea != null ? "assetArea=" + assetArea + ", " : "") +
            (assetAddress != null ? "assetAddress=" + assetAddress + ", " : "") +
            (numberOfAssets != null ? "numberOfAssets=" + numberOfAssets + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
