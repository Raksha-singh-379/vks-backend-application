package com.vgtech.vks.app.service.dto;

import com.vgtech.vks.app.domain.enumeration.LedgerClassification;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.LedgerAccounts} entity.
 */
public class LedgerAccountsDTO implements Serializable {

    private Long id;

    private Long accountNo;

    private String accountName;

    private Double accBalance;

    private String accHeadCode;

    private String ledgerCode;

    private String appCode;

    private LedgerClassification ledgerClassification;

    private String category;

    private Integer level;

    private String year;

    private String accountType;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean isDeleted;

    private String freeField1;

    private String freeField2;

    private SocietyDTO society;

    private LedgerAccountsDTO ledgerAccounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(Double accBalance) {
        this.accBalance = accBalance;
    }

    public String getAccHeadCode() {
        return accHeadCode;
    }

    public void setAccHeadCode(String accHeadCode) {
        this.accHeadCode = accHeadCode;
    }

    public String getLedgerCode() {
        return ledgerCode;
    }

    public void setLedgerCode(String ledgerCode) {
        this.ledgerCode = ledgerCode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public LedgerClassification getLedgerClassification() {
        return ledgerClassification;
    }

    public void setLedgerClassification(LedgerClassification ledgerClassification) {
        this.ledgerClassification = ledgerClassification;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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

    public SocietyDTO getSociety() {
        return society;
    }

    public void setSociety(SocietyDTO society) {
        this.society = society;
    }

    public LedgerAccountsDTO getLedgerAccounts() {
        return ledgerAccounts;
    }

    public void setLedgerAccounts(LedgerAccountsDTO ledgerAccounts) {
        this.ledgerAccounts = ledgerAccounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LedgerAccountsDTO)) {
            return false;
        }

        LedgerAccountsDTO ledgerAccountsDTO = (LedgerAccountsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ledgerAccountsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LedgerAccountsDTO{" +
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
            ", society=" + getSociety() +
            ", ledgerAccounts=" + getLedgerAccounts() +
            "}";
    }
}
