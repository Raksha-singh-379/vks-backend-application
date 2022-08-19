package com.vgtech.vks.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.MemberLandAssets} entity.
 */
public class MemberLandAssetsDTO implements Serializable {

    private Long id;

    private String landType;

    private String landGatNo;

    private Double landAreaInHector;

    private String jindagiPatrakNo;

    private Double jindagiAmount;

    private String assetLandAddress;

    private Double valueOfLand;

    private Boolean assigneeOfLand;

    private Boolean isDeleted;

    private Long mLLoanNo;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

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

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getLandGatNo() {
        return landGatNo;
    }

    public void setLandGatNo(String landGatNo) {
        this.landGatNo = landGatNo;
    }

    public Double getLandAreaInHector() {
        return landAreaInHector;
    }

    public void setLandAreaInHector(Double landAreaInHector) {
        this.landAreaInHector = landAreaInHector;
    }

    public String getJindagiPatrakNo() {
        return jindagiPatrakNo;
    }

    public void setJindagiPatrakNo(String jindagiPatrakNo) {
        this.jindagiPatrakNo = jindagiPatrakNo;
    }

    public Double getJindagiAmount() {
        return jindagiAmount;
    }

    public void setJindagiAmount(Double jindagiAmount) {
        this.jindagiAmount = jindagiAmount;
    }

    public String getAssetLandAddress() {
        return assetLandAddress;
    }

    public void setAssetLandAddress(String assetLandAddress) {
        this.assetLandAddress = assetLandAddress;
    }

    public Double getValueOfLand() {
        return valueOfLand;
    }

    public void setValueOfLand(Double valueOfLand) {
        this.valueOfLand = valueOfLand;
    }

    public Boolean getAssigneeOfLand() {
        return assigneeOfLand;
    }

    public void setAssigneeOfLand(Boolean assigneeOfLand) {
        this.assigneeOfLand = assigneeOfLand;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getmLLoanNo() {
        return mLLoanNo;
    }

    public void setmLLoanNo(Long mLLoanNo) {
        this.mLLoanNo = mLLoanNo;
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
        if (!(o instanceof MemberLandAssetsDTO)) {
            return false;
        }

        MemberLandAssetsDTO memberLandAssetsDTO = (MemberLandAssetsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, memberLandAssetsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberLandAssetsDTO{" +
            "id=" + getId() +
            ", landType='" + getLandType() + "'" +
            ", landGatNo='" + getLandGatNo() + "'" +
            ", landAreaInHector=" + getLandAreaInHector() +
            ", jindagiPatrakNo='" + getJindagiPatrakNo() + "'" +
            ", jindagiAmount=" + getJindagiAmount() +
            ", assetLandAddress='" + getAssetLandAddress() + "'" +
            ", valueOfLand=" + getValueOfLand() +
            ", assigneeOfLand='" + getAssigneeOfLand() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", mLLoanNo=" + getmLLoanNo() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", member=" + getMember() +
            "}";
    }
}
