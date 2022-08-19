package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vgtech.vks.app.domain.enumeration.LoanStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LoanDemand.
 */
@Entity
@Table(name = "loan_demand")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LoanDemand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "loan_demand_amount")
    private Double loanDemandAmount;

    @Column(name = "max_allowed_amount")
    private Double maxAllowedAmount;

    @Column(name = "adjusted_demand")
    private Double adjustedDemand;

    @Column(name = "annual_income")
    private Double annualIncome;

    @Column(name = "cost_of_investment")
    private Double costOfInvestment;

    @Column(name = "demanded_land_area_in_hector")
    private Integer demandedLandAreaInHector;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoanStatus status;

    @Column(name = "season_of_crop_loan")
    private String seasonOfCropLoan;

    @Column(name = "year")
    private String year;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society" }, allowSetters = true)
    private Member member;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society", "bankDhoranDetails", "gRInterestDetails" }, allowSetters = true)
    private SocietyLoanProduct societyLoanProduct;

    @ManyToOne
    @JsonIgnoreProperties(value = { "member" }, allowSetters = true)
    private MemberLandAssets memberLandAssets;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society" }, allowSetters = true)
    private SocietyCropRegistration societyCropRegistration;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LoanDemand id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLoanDemandAmount() {
        return this.loanDemandAmount;
    }

    public LoanDemand loanDemandAmount(Double loanDemandAmount) {
        this.setLoanDemandAmount(loanDemandAmount);
        return this;
    }

    public void setLoanDemandAmount(Double loanDemandAmount) {
        this.loanDemandAmount = loanDemandAmount;
    }

    public Double getMaxAllowedAmount() {
        return this.maxAllowedAmount;
    }

    public LoanDemand maxAllowedAmount(Double maxAllowedAmount) {
        this.setMaxAllowedAmount(maxAllowedAmount);
        return this;
    }

    public void setMaxAllowedAmount(Double maxAllowedAmount) {
        this.maxAllowedAmount = maxAllowedAmount;
    }

    public Double getAdjustedDemand() {
        return this.adjustedDemand;
    }

    public LoanDemand adjustedDemand(Double adjustedDemand) {
        this.setAdjustedDemand(adjustedDemand);
        return this;
    }

    public void setAdjustedDemand(Double adjustedDemand) {
        this.adjustedDemand = adjustedDemand;
    }

    public Double getAnnualIncome() {
        return this.annualIncome;
    }

    public LoanDemand annualIncome(Double annualIncome) {
        this.setAnnualIncome(annualIncome);
        return this;
    }

    public void setAnnualIncome(Double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public Double getCostOfInvestment() {
        return this.costOfInvestment;
    }

    public LoanDemand costOfInvestment(Double costOfInvestment) {
        this.setCostOfInvestment(costOfInvestment);
        return this;
    }

    public void setCostOfInvestment(Double costOfInvestment) {
        this.costOfInvestment = costOfInvestment;
    }

    public Integer getDemandedLandAreaInHector() {
        return this.demandedLandAreaInHector;
    }

    public LoanDemand demandedLandAreaInHector(Integer demandedLandAreaInHector) {
        this.setDemandedLandAreaInHector(demandedLandAreaInHector);
        return this;
    }

    public void setDemandedLandAreaInHector(Integer demandedLandAreaInHector) {
        this.demandedLandAreaInHector = demandedLandAreaInHector;
    }

    public LoanStatus getStatus() {
        return this.status;
    }

    public LoanDemand status(LoanStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public String getSeasonOfCropLoan() {
        return this.seasonOfCropLoan;
    }

    public LoanDemand seasonOfCropLoan(String seasonOfCropLoan) {
        this.setSeasonOfCropLoan(seasonOfCropLoan);
        return this;
    }

    public void setSeasonOfCropLoan(String seasonOfCropLoan) {
        this.seasonOfCropLoan = seasonOfCropLoan;
    }

    public String getYear() {
        return this.year;
    }

    public LoanDemand year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public LoanDemand lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LoanDemand lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public LoanDemand freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public LoanDemand freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public LoanDemand freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LoanDemand member(Member member) {
        this.setMember(member);
        return this;
    }

    public SocietyLoanProduct getSocietyLoanProduct() {
        return this.societyLoanProduct;
    }

    public void setSocietyLoanProduct(SocietyLoanProduct societyLoanProduct) {
        this.societyLoanProduct = societyLoanProduct;
    }

    public LoanDemand societyLoanProduct(SocietyLoanProduct societyLoanProduct) {
        this.setSocietyLoanProduct(societyLoanProduct);
        return this;
    }

    public MemberLandAssets getMemberLandAssets() {
        return this.memberLandAssets;
    }

    public void setMemberLandAssets(MemberLandAssets memberLandAssets) {
        this.memberLandAssets = memberLandAssets;
    }

    public LoanDemand memberLandAssets(MemberLandAssets memberLandAssets) {
        this.setMemberLandAssets(memberLandAssets);
        return this;
    }

    public SocietyCropRegistration getSocietyCropRegistration() {
        return this.societyCropRegistration;
    }

    public void setSocietyCropRegistration(SocietyCropRegistration societyCropRegistration) {
        this.societyCropRegistration = societyCropRegistration;
    }

    public LoanDemand societyCropRegistration(SocietyCropRegistration societyCropRegistration) {
        this.setSocietyCropRegistration(societyCropRegistration);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanDemand)) {
            return false;
        }
        return id != null && id.equals(((LoanDemand) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanDemand{" +
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
            "}";
    }
}
