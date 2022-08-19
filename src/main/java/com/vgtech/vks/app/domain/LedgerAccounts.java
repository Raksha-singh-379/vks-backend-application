package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vgtech.vks.app.domain.enumeration.LedgerClassification;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LedgerAccounts.
 */
@Entity
@Table(name = "ledger_accounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LedgerAccounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_no")
    private Long accountNo;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "acc_balance")
    private Double accBalance;

    @Column(name = "acc_head_code")
    private String accHeadCode;

    @Column(name = "ledger_code")
    private String ledgerCode;

    @Column(name = "app_code")
    private String appCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "ledger_classification")
    private LedgerClassification ledgerClassification;

    @Column(name = "category")
    private String category;

    @Column(name = "level")
    private Integer level;

    @Column(name = "year")
    private String year;

    @Column(name = "account_type")
    private String accountType;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "city", "state", "district", "taluka", "society" }, allowSetters = true)
    private Society society;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society", "ledgerAccounts" }, allowSetters = true)
    private LedgerAccounts ledgerAccounts;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LedgerAccounts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNo() {
        return this.accountNo;
    }

    public LedgerAccounts accountNo(Long accountNo) {
        this.setAccountNo(accountNo);
        return this;
    }

    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public LedgerAccounts accountName(String accountName) {
        this.setAccountName(accountName);
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getAccBalance() {
        return this.accBalance;
    }

    public LedgerAccounts accBalance(Double accBalance) {
        this.setAccBalance(accBalance);
        return this;
    }

    public void setAccBalance(Double accBalance) {
        this.accBalance = accBalance;
    }

    public String getAccHeadCode() {
        return this.accHeadCode;
    }

    public LedgerAccounts accHeadCode(String accHeadCode) {
        this.setAccHeadCode(accHeadCode);
        return this;
    }

    public void setAccHeadCode(String accHeadCode) {
        this.accHeadCode = accHeadCode;
    }

    public String getLedgerCode() {
        return this.ledgerCode;
    }

    public LedgerAccounts ledgerCode(String ledgerCode) {
        this.setLedgerCode(ledgerCode);
        return this;
    }

    public void setLedgerCode(String ledgerCode) {
        this.ledgerCode = ledgerCode;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public LedgerAccounts appCode(String appCode) {
        this.setAppCode(appCode);
        return this;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public LedgerClassification getLedgerClassification() {
        return this.ledgerClassification;
    }

    public LedgerAccounts ledgerClassification(LedgerClassification ledgerClassification) {
        this.setLedgerClassification(ledgerClassification);
        return this;
    }

    public void setLedgerClassification(LedgerClassification ledgerClassification) {
        this.ledgerClassification = ledgerClassification;
    }

    public String getCategory() {
        return this.category;
    }

    public LedgerAccounts category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getLevel() {
        return this.level;
    }

    public LedgerAccounts level(Integer level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getYear() {
        return this.year;
    }

    public LedgerAccounts year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public LedgerAccounts accountType(String accountType) {
        this.setAccountType(accountType);
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public LedgerAccounts lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LedgerAccounts lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public LedgerAccounts createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public LedgerAccounts createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public LedgerAccounts isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public LedgerAccounts freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public LedgerAccounts freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public Society getSociety() {
        return this.society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public LedgerAccounts society(Society society) {
        this.setSociety(society);
        return this;
    }

    public LedgerAccounts getLedgerAccounts() {
        return this.ledgerAccounts;
    }

    public void setLedgerAccounts(LedgerAccounts ledgerAccounts) {
        this.ledgerAccounts = ledgerAccounts;
    }

    public LedgerAccounts ledgerAccounts(LedgerAccounts ledgerAccounts) {
        this.setLedgerAccounts(ledgerAccounts);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LedgerAccounts)) {
            return false;
        }
        return id != null && id.equals(((LedgerAccounts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LedgerAccounts{" +
            "id=" + getId() +
            ", accountNo=" + getAccountNo() +
            ", accountName='" + getAccountName() + "'" +
            ", accBalance=" + getAccBalance() +
            ", accHeadCode='" + getAccHeadCode() + "'" +
            ", ledgerCode='" + getLedgerCode() + "'" +
            ", appCode='" + getAppCode() + "'" +
            ", ledgerClassification='" + getLedgerClassification() + "'" +
            ", category='" + getCategory() + "'" +
            ", level=" + getLevel() +
            ", year='" + getYear() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            "}";
    }
}
