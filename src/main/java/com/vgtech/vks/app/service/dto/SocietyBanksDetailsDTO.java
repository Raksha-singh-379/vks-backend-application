package com.vgtech.vks.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.SocietyBanksDetails} entity.
 */
public class SocietyBanksDetailsDTO implements Serializable {

    private Long id;

    private String bankName;

    private String ifsccode;

    private String branchName;

    @NotNull
    private String accountNumber;

    private Boolean isActive;

    private String accountType;

    private String accHeadCode;

    private String parentAccHeadCode;

    private String accountName;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private SocietyDTO society;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccHeadCode() {
        return accHeadCode;
    }

    public void setAccHeadCode(String accHeadCode) {
        this.accHeadCode = accHeadCode;
    }

    public String getParentAccHeadCode() {
        return parentAccHeadCode;
    }

    public void setParentAccHeadCode(String parentAccHeadCode) {
        this.parentAccHeadCode = parentAccHeadCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public SocietyDTO getSociety() {
        return society;
    }

    public void setSociety(SocietyDTO society) {
        this.society = society;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocietyBanksDetailsDTO)) {
            return false;
        }

        SocietyBanksDetailsDTO societyBanksDetailsDTO = (SocietyBanksDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, societyBanksDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyBanksDetailsDTO{" +
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
            ", society=" + getSociety() +
            "}";
    }
}
