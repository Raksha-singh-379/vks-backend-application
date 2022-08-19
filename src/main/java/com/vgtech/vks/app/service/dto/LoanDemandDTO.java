package com.vgtech.vks.app.service.dto;

import com.vgtech.vks.app.domain.enumeration.LoanStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.LoanDemand} entity.
 */
public class LoanDemandDTO implements Serializable {

    private Long id;

    private Double loanDemandAmount;

    private Double maxAllowedAmount;

    private Double adjustedDemand;

    private Double annualIncome;

    private Double costOfInvestment;

    private Integer demandedLandAreaInHector;

    private LoanStatus status;

    private String seasonOfCropLoan;

    private String year;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private MemberDTO member;

    private SocietyLoanProductDTO societyLoanProduct;

    private MemberLandAssetsDTO memberLandAssets;

    private SocietyCropRegistrationDTO societyCropRegistration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLoanDemandAmount() {
        return loanDemandAmount;
    }

    public void setLoanDemandAmount(Double loanDemandAmount) {
        this.loanDemandAmount = loanDemandAmount;
    }

    public Double getMaxAllowedAmount() {
        return maxAllowedAmount;
    }

    public void setMaxAllowedAmount(Double maxAllowedAmount) {
        this.maxAllowedAmount = maxAllowedAmount;
    }

    public Double getAdjustedDemand() {
        return adjustedDemand;
    }

    public void setAdjustedDemand(Double adjustedDemand) {
        this.adjustedDemand = adjustedDemand;
    }

    public Double getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public Double getCostOfInvestment() {
        return costOfInvestment;
    }

    public void setCostOfInvestment(Double costOfInvestment) {
        this.costOfInvestment = costOfInvestment;
    }

    public Integer getDemandedLandAreaInHector() {
        return demandedLandAreaInHector;
    }

    public void setDemandedLandAreaInHector(Integer demandedLandAreaInHector) {
        this.demandedLandAreaInHector = demandedLandAreaInHector;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public String getSeasonOfCropLoan() {
        return seasonOfCropLoan;
    }

    public void setSeasonOfCropLoan(String seasonOfCropLoan) {
        this.seasonOfCropLoan = seasonOfCropLoan;
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

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public SocietyLoanProductDTO getSocietyLoanProduct() {
        return societyLoanProduct;
    }

    public void setSocietyLoanProduct(SocietyLoanProductDTO societyLoanProduct) {
        this.societyLoanProduct = societyLoanProduct;
    }

    public MemberLandAssetsDTO getMemberLandAssets() {
        return memberLandAssets;
    }

    public void setMemberLandAssets(MemberLandAssetsDTO memberLandAssets) {
        this.memberLandAssets = memberLandAssets;
    }

    public SocietyCropRegistrationDTO getSocietyCropRegistration() {
        return societyCropRegistration;
    }

    public void setSocietyCropRegistration(SocietyCropRegistrationDTO societyCropRegistration) {
        this.societyCropRegistration = societyCropRegistration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanDemandDTO)) {
            return false;
        }

        LoanDemandDTO loanDemandDTO = (LoanDemandDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loanDemandDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanDemandDTO{" +
            "id=" + getId() +
            ", loanDemandAmount=" + getLoanDemandAmount() +
            ", maxAllowedAmount=" + getMaxAllowedAmount() +
            ", adjustedDemand=" + getAdjustedDemand() +
            ", annualIncome=" + getAnnualIncome() +
            ", costOfInvestment=" + getCostOfInvestment() +
            ", demandedLandAreaInHector=" + getDemandedLandAreaInHector() +
            ", status='" + getStatus() + "'" +
            ", seasonOfCropLoan='" + getSeasonOfCropLoan() + "'" +
            ", year='" + getYear() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", member=" + getMember() +
            ", societyLoanProduct=" + getSocietyLoanProduct() +
            ", memberLandAssets=" + getMemberLandAssets() +
            ", societyCropRegistration=" + getSocietyCropRegistration() +
            "}";
    }
}
