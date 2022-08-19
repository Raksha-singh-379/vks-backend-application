package com.vgtech.vks.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.SocietyAssetsData} entity.
 */
public class SocietyAssetsDataDTO implements Serializable {

    private Long id;

    private Double amount;

    private Long balanceQuantity;

    private Double balanceValue;

    private String billNo;

    private String mode;

    private Double cost;

    private String transactionType;

    private Instant transactionDate;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean isDeleted;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private SocietyAssetsDTO societyAssets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getBalanceQuantity() {
        return balanceQuantity;
    }

    public void setBalanceQuantity(Long balanceQuantity) {
        this.balanceQuantity = balanceQuantity;
    }

    public Double getBalanceValue() {
        return balanceValue;
    }

    public void setBalanceValue(Double balanceValue) {
        this.balanceValue = balanceValue;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
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

    public SocietyAssetsDTO getSocietyAssets() {
        return societyAssets;
    }

    public void setSocietyAssets(SocietyAssetsDTO societyAssets) {
        this.societyAssets = societyAssets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocietyAssetsDataDTO)) {
            return false;
        }

        SocietyAssetsDataDTO societyAssetsDataDTO = (SocietyAssetsDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, societyAssetsDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyAssetsDataDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", balanceQuantity=" + getBalanceQuantity() +
            ", balanceValue=" + getBalanceValue() +
            ", billNo='" + getBillNo() + "'" +
            ", mode='" + getMode() + "'" +
            ", cost=" + getCost() +
            ", transactionType='" + getTransactionType() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", societyAssets=" + getSocietyAssets() +
            "}";
    }
}
