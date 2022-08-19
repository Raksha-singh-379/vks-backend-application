package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SocietyAssetsData.
 */
@Entity
@Table(name = "society_assets_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SocietyAssetsData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "balance_quantity")
    private Long balanceQuantity;

    @Column(name = "balance_value")
    private Double balanceValue;

    @Column(name = "bill_no")
    private String billNo;

    @Column(name = "mode")
    private String mode;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "transaction_date")
    private Instant transactionDate;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society" }, allowSetters = true)
    private SocietyAssets societyAssets;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SocietyAssetsData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return this.amount;
    }

    public SocietyAssetsData amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getBalanceQuantity() {
        return this.balanceQuantity;
    }

    public SocietyAssetsData balanceQuantity(Long balanceQuantity) {
        this.setBalanceQuantity(balanceQuantity);
        return this;
    }

    public void setBalanceQuantity(Long balanceQuantity) {
        this.balanceQuantity = balanceQuantity;
    }

    public Double getBalanceValue() {
        return this.balanceValue;
    }

    public SocietyAssetsData balanceValue(Double balanceValue) {
        this.setBalanceValue(balanceValue);
        return this;
    }

    public void setBalanceValue(Double balanceValue) {
        this.balanceValue = balanceValue;
    }

    public String getBillNo() {
        return this.billNo;
    }

    public SocietyAssetsData billNo(String billNo) {
        this.setBillNo(billNo);
        return this;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getMode() {
        return this.mode;
    }

    public SocietyAssetsData mode(String mode) {
        this.setMode(mode);
        return this;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Double getCost() {
        return this.cost;
    }

    public SocietyAssetsData cost(Double cost) {
        this.setCost(cost);
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public SocietyAssetsData transactionType(String transactionType) {
        this.setTransactionType(transactionType);
        return this;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Instant getTransactionDate() {
        return this.transactionDate;
    }

    public SocietyAssetsData transactionDate(Instant transactionDate) {
        this.setTransactionDate(transactionDate);
        return this;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public SocietyAssetsData lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public SocietyAssetsData lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public SocietyAssetsData createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public SocietyAssetsData createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public SocietyAssetsData isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public SocietyAssetsData freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public SocietyAssetsData freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public SocietyAssetsData freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public SocietyAssets getSocietyAssets() {
        return this.societyAssets;
    }

    public void setSocietyAssets(SocietyAssets societyAssets) {
        this.societyAssets = societyAssets;
    }

    public SocietyAssetsData societyAssets(SocietyAssets societyAssets) {
        this.setSocietyAssets(societyAssets);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocietyAssetsData)) {
            return false;
        }
        return id != null && id.equals(((SocietyAssetsData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyAssetsData{" +
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
            "}";
    }
}
