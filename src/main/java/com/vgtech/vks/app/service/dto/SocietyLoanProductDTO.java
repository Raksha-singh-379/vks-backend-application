package com.vgtech.vks.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.SocietyLoanProduct} entity.
 */
public class SocietyLoanProductDTO implements Serializable {

    private Long id;

    private String productName;

    private String accHeadCode;

    private Double borrowingInterestRate;

    private Integer duration;

    private Double interestRate;

    private Instant lastDateOfRepayment;

    private String loanNumber;

    private Double maxLoanAmt;

    private Integer noOfDisbursement;

    private Integer noOfInstallment;

    private String parentAccHeadCode;

    private Long parentAccHeadId;

    private Double penaltyInterest;

    private String productType;

    private Instant resolutionDate;

    private String resolutionNo;

    private String status;

    private Double surcharge;

    private Long unitSize;

    private Instant validFrom;

    private Instant validTo;

    private Instant createdOn;

    private String createdBy;

    private Boolean isActivate;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private SocietyDTO society;

    private BankDhoranDetailsDTO bankDhoranDetails;

    private GRInterestDetailsDTO gRInterestDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAccHeadCode() {
        return accHeadCode;
    }

    public void setAccHeadCode(String accHeadCode) {
        this.accHeadCode = accHeadCode;
    }

    public Double getBorrowingInterestRate() {
        return borrowingInterestRate;
    }

    public void setBorrowingInterestRate(Double borrowingInterestRate) {
        this.borrowingInterestRate = borrowingInterestRate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Instant getLastDateOfRepayment() {
        return lastDateOfRepayment;
    }

    public void setLastDateOfRepayment(Instant lastDateOfRepayment) {
        this.lastDateOfRepayment = lastDateOfRepayment;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public Double getMaxLoanAmt() {
        return maxLoanAmt;
    }

    public void setMaxLoanAmt(Double maxLoanAmt) {
        this.maxLoanAmt = maxLoanAmt;
    }

    public Integer getNoOfDisbursement() {
        return noOfDisbursement;
    }

    public void setNoOfDisbursement(Integer noOfDisbursement) {
        this.noOfDisbursement = noOfDisbursement;
    }

    public Integer getNoOfInstallment() {
        return noOfInstallment;
    }

    public void setNoOfInstallment(Integer noOfInstallment) {
        this.noOfInstallment = noOfInstallment;
    }

    public String getParentAccHeadCode() {
        return parentAccHeadCode;
    }

    public void setParentAccHeadCode(String parentAccHeadCode) {
        this.parentAccHeadCode = parentAccHeadCode;
    }

    public Long getParentAccHeadId() {
        return parentAccHeadId;
    }

    public void setParentAccHeadId(Long parentAccHeadId) {
        this.parentAccHeadId = parentAccHeadId;
    }

    public Double getPenaltyInterest() {
        return penaltyInterest;
    }

    public void setPenaltyInterest(Double penaltyInterest) {
        this.penaltyInterest = penaltyInterest;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Instant getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Instant resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getResolutionNo() {
        return resolutionNo;
    }

    public void setResolutionNo(String resolutionNo) {
        this.resolutionNo = resolutionNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(Double surcharge) {
        this.surcharge = surcharge;
    }

    public Long getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(Long unitSize) {
        this.unitSize = unitSize;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getIsActivate() {
        return isActivate;
    }

    public void setIsActivate(Boolean isActivate) {
        this.isActivate = isActivate;
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

    public BankDhoranDetailsDTO getBankDhoranDetails() {
        return bankDhoranDetails;
    }

    public void setBankDhoranDetails(BankDhoranDetailsDTO bankDhoranDetails) {
        this.bankDhoranDetails = bankDhoranDetails;
    }

    public GRInterestDetailsDTO getgRInterestDetails() {
        return gRInterestDetails;
    }

    public void setgRInterestDetails(GRInterestDetailsDTO gRInterestDetails) {
        this.gRInterestDetails = gRInterestDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocietyLoanProductDTO)) {
            return false;
        }

        SocietyLoanProductDTO societyLoanProductDTO = (SocietyLoanProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, societyLoanProductDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyLoanProductDTO{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", accHeadCode='" + getAccHeadCode() + "'" +
            ", borrowingInterestRate=" + getBorrowingInterestRate() +
            ", duration=" + getDuration() +
            ", interestRate=" + getInterestRate() +
            ", lastDateOfRepayment='" + getLastDateOfRepayment() + "'" +
            ", loanNumber='" + getLoanNumber() + "'" +
            ", maxLoanAmt=" + getMaxLoanAmt() +
            ", noOfDisbursement=" + getNoOfDisbursement() +
            ", noOfInstallment=" + getNoOfInstallment() +
            ", parentAccHeadCode='" + getParentAccHeadCode() + "'" +
            ", parentAccHeadId=" + getParentAccHeadId() +
            ", penaltyInterest=" + getPenaltyInterest() +
            ", productType='" + getProductType() + "'" +
            ", resolutionDate='" + getResolutionDate() + "'" +
            ", resolutionNo='" + getResolutionNo() + "'" +
            ", status='" + getStatus() + "'" +
            ", surcharge=" + getSurcharge() +
            ", unitSize=" + getUnitSize() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", isActivate='" + getIsActivate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", society=" + getSociety() +
            ", bankDhoranDetails=" + getBankDhoranDetails() +
            ", gRInterestDetails=" + getgRInterestDetails() +
            "}";
    }
}
