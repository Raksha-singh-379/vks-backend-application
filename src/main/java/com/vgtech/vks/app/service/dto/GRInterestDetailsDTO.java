package com.vgtech.vks.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.GRInterestDetails} entity.
 */
public class GRInterestDetailsDTO implements Serializable {

    private Long id;

    private String loanGrName;

    private String criteria;

    private String productType;

    private Boolean isActivated;

    private Double borrowingInterestRate;

    private Double interestOnLoan;

    private Double penaltyInterest;

    private Double surcharge;

    private Double loanDuration;

    private Integer numberOFInstallment;

    private Double extendedInterstRate;

    private Double centralGovSubsidyInterest;

    private Double distBankSubsidyInterest;

    private Double borrowerInterest;

    private Double stateGovSubsidyInterest;

    private String year;

    private Instant startDate;

    private Instant endDate;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean isDeleted;

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

    public String getLoanGrName() {
        return loanGrName;
    }

    public void setLoanGrName(String loanGrName) {
        this.loanGrName = loanGrName;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Boolean getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public Double getBorrowingInterestRate() {
        return borrowingInterestRate;
    }

    public void setBorrowingInterestRate(Double borrowingInterestRate) {
        this.borrowingInterestRate = borrowingInterestRate;
    }

    public Double getInterestOnLoan() {
        return interestOnLoan;
    }

    public void setInterestOnLoan(Double interestOnLoan) {
        this.interestOnLoan = interestOnLoan;
    }

    public Double getPenaltyInterest() {
        return penaltyInterest;
    }

    public void setPenaltyInterest(Double penaltyInterest) {
        this.penaltyInterest = penaltyInterest;
    }

    public Double getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(Double surcharge) {
        this.surcharge = surcharge;
    }

    public Double getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(Double loanDuration) {
        this.loanDuration = loanDuration;
    }

    public Integer getNumberOFInstallment() {
        return numberOFInstallment;
    }

    public void setNumberOFInstallment(Integer numberOFInstallment) {
        this.numberOFInstallment = numberOFInstallment;
    }

    public Double getExtendedInterstRate() {
        return extendedInterstRate;
    }

    public void setExtendedInterstRate(Double extendedInterstRate) {
        this.extendedInterstRate = extendedInterstRate;
    }

    public Double getCentralGovSubsidyInterest() {
        return centralGovSubsidyInterest;
    }

    public void setCentralGovSubsidyInterest(Double centralGovSubsidyInterest) {
        this.centralGovSubsidyInterest = centralGovSubsidyInterest;
    }

    public Double getDistBankSubsidyInterest() {
        return distBankSubsidyInterest;
    }

    public void setDistBankSubsidyInterest(Double distBankSubsidyInterest) {
        this.distBankSubsidyInterest = distBankSubsidyInterest;
    }

    public Double getBorrowerInterest() {
        return borrowerInterest;
    }

    public void setBorrowerInterest(Double borrowerInterest) {
        this.borrowerInterest = borrowerInterest;
    }

    public Double getStateGovSubsidyInterest() {
        return stateGovSubsidyInterest;
    }

    public void setStateGovSubsidyInterest(Double stateGovSubsidyInterest) {
        this.stateGovSubsidyInterest = stateGovSubsidyInterest;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
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
        if (!(o instanceof GRInterestDetailsDTO)) {
            return false;
        }

        GRInterestDetailsDTO gRInterestDetailsDTO = (GRInterestDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gRInterestDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GRInterestDetailsDTO{" +
            "id=" + getId() +
            ", loanGrName='" + getLoanGrName() + "'" +
            ", criteria='" + getCriteria() + "'" +
            ", productType='" + getProductType() + "'" +
            ", isActivated='" + getIsActivated() + "'" +
            ", borrowingInterestRate=" + getBorrowingInterestRate() +
            ", interestOnLoan=" + getInterestOnLoan() +
            ", penaltyInterest=" + getPenaltyInterest() +
            ", surcharge=" + getSurcharge() +
            ", loanDuration=" + getLoanDuration() +
            ", numberOFInstallment=" + getNumberOFInstallment() +
            ", extendedInterstRate=" + getExtendedInterstRate() +
            ", centralGovSubsidyInterest=" + getCentralGovSubsidyInterest() +
            ", distBankSubsidyInterest=" + getDistBankSubsidyInterest() +
            ", borrowerInterest=" + getBorrowerInterest() +
            ", stateGovSubsidyInterest=" + getStateGovSubsidyInterest() +
            ", year='" + getYear() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", society=" + getSociety() +
            "}";
    }
}
