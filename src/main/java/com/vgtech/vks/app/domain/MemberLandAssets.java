package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MemberLandAssets.
 */
@Entity
@Table(name = "member_land_assets")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MemberLandAssets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "land_type")
    private String landType;

    @Column(name = "land_gat_no")
    private String landGatNo;

    @Column(name = "land_area_in_hector")
    private Double landAreaInHector;

    @Column(name = "jindagi_patrak_no")
    private String jindagiPatrakNo;

    @Column(name = "jindagi_amount")
    private Double jindagiAmount;

    @Column(name = "asset_land_address")
    private String assetLandAddress;

    @Column(name = "value_of_land")
    private Double valueOfLand;

    @Column(name = "assignee_of_land")
    private Boolean assigneeOfLand;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "m_l_loan_no")
    private Long mLLoanNo;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society" }, allowSetters = true)
    private Member member;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MemberLandAssets id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLandType() {
        return this.landType;
    }

    public MemberLandAssets landType(String landType) {
        this.setLandType(landType);
        return this;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getLandGatNo() {
        return this.landGatNo;
    }

    public MemberLandAssets landGatNo(String landGatNo) {
        this.setLandGatNo(landGatNo);
        return this;
    }

    public void setLandGatNo(String landGatNo) {
        this.landGatNo = landGatNo;
    }

    public Double getLandAreaInHector() {
        return this.landAreaInHector;
    }

    public MemberLandAssets landAreaInHector(Double landAreaInHector) {
        this.setLandAreaInHector(landAreaInHector);
        return this;
    }

    public void setLandAreaInHector(Double landAreaInHector) {
        this.landAreaInHector = landAreaInHector;
    }

    public String getJindagiPatrakNo() {
        return this.jindagiPatrakNo;
    }

    public MemberLandAssets jindagiPatrakNo(String jindagiPatrakNo) {
        this.setJindagiPatrakNo(jindagiPatrakNo);
        return this;
    }

    public void setJindagiPatrakNo(String jindagiPatrakNo) {
        this.jindagiPatrakNo = jindagiPatrakNo;
    }

    public Double getJindagiAmount() {
        return this.jindagiAmount;
    }

    public MemberLandAssets jindagiAmount(Double jindagiAmount) {
        this.setJindagiAmount(jindagiAmount);
        return this;
    }

    public void setJindagiAmount(Double jindagiAmount) {
        this.jindagiAmount = jindagiAmount;
    }

    public String getAssetLandAddress() {
        return this.assetLandAddress;
    }

    public MemberLandAssets assetLandAddress(String assetLandAddress) {
        this.setAssetLandAddress(assetLandAddress);
        return this;
    }

    public void setAssetLandAddress(String assetLandAddress) {
        this.assetLandAddress = assetLandAddress;
    }

    public Double getValueOfLand() {
        return this.valueOfLand;
    }

    public MemberLandAssets valueOfLand(Double valueOfLand) {
        this.setValueOfLand(valueOfLand);
        return this;
    }

    public void setValueOfLand(Double valueOfLand) {
        this.valueOfLand = valueOfLand;
    }

    public Boolean getAssigneeOfLand() {
        return this.assigneeOfLand;
    }

    public MemberLandAssets assigneeOfLand(Boolean assigneeOfLand) {
        this.setAssigneeOfLand(assigneeOfLand);
        return this;
    }

    public void setAssigneeOfLand(Boolean assigneeOfLand) {
        this.assigneeOfLand = assigneeOfLand;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public MemberLandAssets isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getmLLoanNo() {
        return this.mLLoanNo;
    }

    public MemberLandAssets mLLoanNo(Long mLLoanNo) {
        this.setmLLoanNo(mLLoanNo);
        return this;
    }

    public void setmLLoanNo(Long mLLoanNo) {
        this.mLLoanNo = mLLoanNo;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public MemberLandAssets lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public MemberLandAssets lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public MemberLandAssets createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public MemberLandAssets createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public MemberLandAssets freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public MemberLandAssets freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public MemberLandAssets freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public MemberLandAssets member(Member member) {
        this.setMember(member);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberLandAssets)) {
            return false;
        }
        return id != null && id.equals(((MemberLandAssets) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberLandAssets{" +
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
            "}";
    }
}
