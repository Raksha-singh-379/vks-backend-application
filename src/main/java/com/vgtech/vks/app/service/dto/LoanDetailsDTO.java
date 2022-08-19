package com.vgtech.vks.app.service.dto;

import com.vgtech.vks.app.domain.enumeration.LoanStatus;
import com.vgtech.vks.app.domain.enumeration.LoanType;
import com.vgtech.vks.app.domain.enumeration.NpaClassification;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.LoanDetails} entity.
 */
public class LoanDetailsDTO implements Serializable {

    private Long id;

    private Double loanAmount;

    private String loanAccountNo;

    private LoanType loanType;

    private LoanStatus status;

    private Instant loanStartDate;

    private Instant loanEndDate;

    private Instant loanPlannedClosureDate;

    private Instant loanCloserDate;

    private Instant loanEffectiveDate;

    private NpaClassification loanClassification;

    private String resolutionNo;

    private Instant resolutionDate;

    private Double costOfInvestment;

    private Double loanBenefitingArea;

    private Long dccbLoanNo;

    private Long mortgageDeedNo;

    private Instant mortgageDate;

    private Double extentMorgageValue;

    private String parentAccHeadCode;

    private String loanAccountName;

    private Double disbursementAmt;

    private String disbursementStatus;

    private String year;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private LoanDemandDTO loanDemand;

    private MemberDTO member;

    private LoanDemandDTO loanDemand;

    private SocietyLoanProductDTO societyLoanProduct;

    private BankDhoranDetailsDTO bankDhoranDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanAccountNo() {
        return loanAccountNo;
    }

    public void setLoanAccountNo(String loanAccountNo) {
        this.loanAccountNo = loanAccountNo;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Instant getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(Instant loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public Instant getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(Instant loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public Instant getLoanPlannedClosureDate() {
        return loanPlannedClosureDate;
    }

    public void setLoanPlannedClosureDate(Instant loanPlannedClosureDate) {
        this.loanPlannedClosureDate = loanPlannedClosureDate;
    }

    public Instant getLoanCloserDate() {
        return loanCloserDate;
    }

    public void setLoanCloserDate(Instant loanCloserDate) {
        this.loanCloserDate = loanCloserDate;
    }

    public Instant getLoanEffectiveDate() {
        return loanEffectiveDate;
    }

    public void setLoanEffectiveDate(Instant loanEffectiveDate) {
        this.loanEffectiveDate = loanEffectiveDate;
    }

    public NpaClassification getLoanClassification() {
        return loanClassification;
    }

    public void setLoanClassification(NpaClassification loanClassification) {
        this.loanClassification = loanClassification;
    }

    public String getResolutionNo() {
        return resolutionNo;
    }

    public void setResolutionNo(String resolutionNo) {
        this.resolutionNo = resolutionNo;
    }

    public Instant getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Instant resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public Double getCostOfInvestment() {
        return costOfInvestment;
    }

    public void setCostOfInvestment(Double costOfInvestment) {
        this.costOfInvestment = costOfInvestment;
    }

    public Double getLoanBenefitingArea() {
        return loanBenefitingArea;
    }

    public void setLoanBenefitingArea(Double loanBenefitingArea) {
        this.loanBenefitingArea = loanBenefitingArea;
    }

    public Long getDccbLoanNo() {
        return dccbLoanNo;
    }

    public void setDccbLoanNo(Long dccbLoanNo) {
        this.dccbLoanNo = dccbLoanNo;
    }

    public Long getMortgageDeedNo() {
        return mortgageDeedNo;
    }

    public void setMortgageDeedNo(Long mortgageDeedNo) {
        this.mortgageDeedNo = mortgageDeedNo;
    }

    public Instant getMortgageDate() {
        return mortgageDate;
    }

    public void setMortgageDate(Instant mortgageDate) {
        this.mortgageDate = mortgageDate;
    }

    public Double getExtentMorgageValue() {
        return extentMorgageValue;
    }

    public void setExtentMorgageValue(Double extentMorgageValue) {
        this.extentMorgageValue = extentMorgageValue;
    }

    public String getParentAccHeadCode() {
        return parentAccHeadCode;
    }

    public void setParentAccHeadCode(String parentAccHeadCode) {
        this.parentAccHeadCode = parentAccHeadCode;
    }

    public String getLoanAccountName() {
        return loanAccountName;
    }

    public void setLoanAccountName(String loanAccountName) {
        this.loanAccountName = loanAccountName;
    }

    public Double getDisbursementAmt() {
        return disbursementAmt;
    }

    public void setDisbursementAmt(Double disbursementAmt) {
        this.disbursementAmt = disbursementAmt;
    }

    public String getDisbursementStatus() {
        return disbursementStatus;
    }

    public void setDisbursementStatus(String disbursementStatus) {
        this.disbursementStatus = disbursementStatus;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public LoanDemandDTO getLoanDemand() {
        return loanDemand;
    }

    public void setLoanDemand(LoanDemandDTO loanDemand) {
        this.loanDemand = loanDemand;
    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public LoanDemandDTO getLoanDemand() {
        return loanDemand;
    }

    public void setLoanDemand(LoanDemandDTO loanDemand) {
        this.loanDemand = loanDemand;
    }

    public SocietyLoanProductDTO getSocietyLoanProduct() {
        return societyLoanProduct;
    }

    public void setSocietyLoanProduct(SocietyLoanProductDTO societyLoanProduct) {
        this.societyLoanProduct = societyLoanProduct;
    }

    public BankDhoranDetailsDTO getBankDhoranDetails() {
        return bankDhoranDetails;
    }

    public void setBankDhoranDetails(BankDhoranDetailsDTO bankDhoranDetails) {
        this.bankDhoranDetails = bankDhoranDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanDetailsDTO)) {
            return false;
        }

        LoanDetailsDTO loanDetailsDTO = (LoanDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loanDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanDetailsDTO{" +
            "id=" + getId() +
            ", loanAmount=" + getLoanAmount() +
            ", loanAccountNo='" + getLoanAccountNo() + "'" +
            ", loanType='" + getLoanType() + "'" +
            ", status='" + getStatus() + "'" +
            ", loanStartDate='" + getLoanStartDate() + "'" +
            ", loanEndDate='" + getLoanEndDate() + "'" +
            ", loanPlannedClosureDate='" + getLoanPlannedClosureDate() + "'" +
            ", loanCloserDate='" + getLoanCloserDate() + "'" +
            ", loanEffectiveDate='" + getLoanEffectiveDate() + "'" +
            ", loanClassification='" + getLoanClassification() + "'" +
            ", resolutionNo='" + getResolutionNo() + "'" +
            ", resolutionDate='" + getResolutionDate() + "'" +
            ", costOfInvestment=" + getCostOfInvestment() +
            ", loanBenefitingArea=" + getLoanBenefitingArea() +
            ", dccbLoanNo=" + getDccbLoanNo() +
            ", mortgageDeedNo=" + getMortgageDeedNo() +
            ", mortgageDate='" + getMortgageDate() + "'" +
            ", extentMorgageValue=" + getExtentMorgageValue() +
            ", parentAccHeadCode='" + getParentAccHeadCode() + "'" +
            ", loanAccountName='" + getLoanAccountName() + "'" +
            ", disbursementAmt=" + getDisbursementAmt() +
            ", disbursementStatus='" + getDisbursementStatus() + "'" +
            ", year='" + getYear() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", loanDemand=" + getLoanDemand() +
            ", member=" + getMember() +
            ", loanDemand=" + getLoanDemand() +
            ", societyLoanProduct=" + getSocietyLoanProduct() +
            ", bankDhoranDetails=" + getBankDhoranDetails() +
            "}";
    }
}
