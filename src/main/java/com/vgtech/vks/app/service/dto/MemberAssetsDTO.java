package com.vgtech.vks.app.service.dto;

import com.vgtech.vks.app.domain.enumeration.AssetType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.MemberAssets} entity.
 */
public class MemberAssetsDTO implements Serializable {

    private Long id;

    private Double assetAmount;

    private AssetType assetType;

    private Integer assetArea;

    private String assetAddress;

    private Integer numberOfAssets;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean isDeleted;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private MemberDTO member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAssetAmount() {
        return assetAmount;
    }

    public void setAssetAmount(Double assetAmount) {
        this.assetAmount = assetAmount;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public Integer getAssetArea() {
        return assetArea;
    }

    public void setAssetArea(Integer assetArea) {
        this.assetArea = assetArea;
    }

    public String getAssetAddress() {
        return assetAddress;
    }

    public void setAssetAddress(String assetAddress) {
        this.assetAddress = assetAddress;
    }

    public Integer getNumberOfAssets() {
        return numberOfAssets;
    }

    public void setNumberOfAssets(Integer numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return freeField2;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return freeField3;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberAssetsDTO)) {
            return false;
        }

        MemberAssetsDTO memberAssetsDTO = (MemberAssetsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, memberAssetsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberAssetsDTO{" +
            "id=" + getId() +
            ", assetAmount=" + getAssetAmount() +
            ", assetType='" + getAssetType() + "'" +
            ", assetArea=" + getAssetArea() +
            ", assetAddress='" + getAssetAddress() + "'" +
            ", numberOfAssets=" + getNumberOfAssets() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", member=" + getMember() +
            "}";
    }
}
