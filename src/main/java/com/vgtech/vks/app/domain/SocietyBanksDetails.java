package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SocietyBanksDetails.
 */
@Entity
@Table(name = "society_banks_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SocietyBanksDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "ifsccode")
    private String ifsccode;

    @Column(name = "branch_name")
    private String branchName;

    @NotNull
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "acc_head_code")
    private String accHeadCode;

    @Column(name = "parent_acc_head_code")
    private String parentAccHeadCode;

    @Column(name = "account_name")
    private String accountName;

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
    @JsonIgnoreProperties(value = { "city", "state", "district", "taluka", "society" }, allowSetters = true)
    private Society society;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SocietyBanksDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return this.bankName;
    }

    public SocietyBanksDetails bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfsccode() {
        return this.ifsccode;
    }

    public SocietyBanksDetails ifsccode(String ifsccode) {
        this.setIfsccode(ifsccode);
        return this;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public SocietyBanksDetails branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public SocietyBanksDetails accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public SocietyBanksDetails isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public SocietyBanksDetails accountType(String accountType) {
        this.setAccountType(accountType);
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccHeadCode() {
        return this.accHeadCode;
    }

    public SocietyBanksDetails accHeadCode(String accHeadCode) {
        this.setAccHeadCode(accHeadCode);
        return this;
    }

    public void setAccHeadCode(String accHeadCode) {
        this.accHeadCode = accHeadCode;
    }

    public String getParentAccHeadCode() {
        return this.parentAccHeadCode;
    }

    public SocietyBanksDetails parentAccHeadCode(String parentAccHeadCode) {
        this.setParentAccHeadCode(parentAccHeadCode);
        return this;
    }

    public void setParentAccHeadCode(String parentAccHeadCode) {
        this.parentAccHeadCode = parentAccHeadCode;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public SocietyBanksDetails accountName(String accountName) {
        this.setAccountName(accountName);
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public SocietyBanksDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public SocietyBanksDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public SocietyBanksDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public SocietyBanksDetails createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public SocietyBanksDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public SocietyBanksDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public SocietyBanksDetails freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public Society getSociety() {
        return this.society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public SocietyBanksDetails society(Society society) {
        this.setSociety(society);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocietyBanksDetails)) {
            return false;
        }
        return id != null && id.equals(((SocietyBanksDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyBanksDetails{" +
            "id=" + getId() +
            ", bankName='" + getBankName() + "'" +
            ", ifsccode='" + getIfsccode() + "'" +
            ", branchName='" + getBranchName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", accHeadCode='" + getAccHeadCode() + "'" +
            ", parentAccHeadCode='" + getParentAccHeadCode() + "'" +
            ", accountName='" + getAccountName() + "'" +
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
