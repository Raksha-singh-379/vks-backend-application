package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vgtech.vks.app.domain.enumeration.LoanStatus;
import com.vgtech.vks.app.domain.enumeration.LoanType;
import com.vgtech.vks.app.domain.enumeration.NpaClassification;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LoanDetails.
 */
@Entity
@Table(name = "loan_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LoanDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "loan_account_no")
    private String loanAccountNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type")
    private LoanType loanType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoanStatus status;

    @Column(name = "loan_start_date")
    private Instant loanStartDate;

    @Column(name = "loan_end_date")
    private Instant loanEndDate;

    @Column(name = "loan_planned_closure_date")
    private Instant loanPlannedClosureDate;

    @Column(name = "loan_closer_date")
    private Instant loanCloserDate;

    @Column(name = "loan_effective_date")
    private Instant loanEffectiveDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_classification")
    private NpaClassification loanClassification;

    @Column(name = "resolution_no")
    private String resolutionNo;

    @Column(name = "resolution_date")
    private Instant resolutionDate;

    @Column(name = "cost_of_investment")
    private Double costOfInvestment;

    @Column(name = "loan_benefiting_area")
    private Double loanBenefitingArea;

    @Column(name = "dccb_loan_no")
    private Long dccbLoanNo;

    @Column(name = "mortgage_deed_no")
    private Long mortgageDeedNo;

    @Column(name = "mortgage_date")
    private Instant mortgageDate;

    @Column(name = "extent_morgage_value")
    private Double extentMorgageValue;

    @Column(name = "parent_acc_head_code")
    private String parentAccHeadCode;

    @Column(name = "loan_account_name")
    private String loanAccountName;

    @Column(name = "disbursement_amt")
    private Double disbursementAmt;

    @Column(name = "disbursement_status")
    private String disbursementStatus;

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

    @JsonIgnoreProperties(value = { "member", "societyLoanProduct", "memberLandAssets", "societyCropRegistration" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    @ManyToOne
    private LoanDemand loanDemand;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society" }, allowSetters = true)
    private Member member;

    @JsonIgnoreProperties(value = { "member", "societyLoanProduct", "memberLandAssets", "societyCropRegistration" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    @ManyToOne
    @JsonIgnoreProperties(value = { "member", "societyLoanProduct", "memberLandAssets", "societyCropRegistration" }, allowSetters = true)
    private LoanDemand loanDemand;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society", "bankDhoranDetails", "gRInterestDetails" }, allowSetters = true)
    private SocietyLoanProduct societyLoanProduct;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society" }, allowSetters = true)
    private BankDhoranDetails bankDhoranDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LoanDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLoanAmount() {
        return this.loanAmount;
    }

    public LoanDetails loanAmount(Double loanAmount) {
        this.setLoanAmount(loanAmount);
        return this;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanAccountNo() {
        return this.loanAccountNo;
    }

    public LoanDetails loanAccountNo(String loanAccountNo) {
        this.setLoanAccountNo(loanAccountNo);
        return this;
    }

    public void setLoanAccountNo(String loanAccountNo) {
        this.loanAccountNo = loanAccountNo;
    }

    public LoanType getLoanType() {
        return this.loanType;
    }

    public LoanDetails loanType(LoanType loanType) {
        this.setLoanType(loanType);
        return this;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public LoanStatus getStatus() {
        return this.status;
    }

    public LoanDetails status(LoanStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Instant getLoanStartDate() {
        return this.loanStartDate;
    }

    public LoanDetails loanStartDate(Instant loanStartDate) {
        this.setLoanStartDate(loanStartDate);
        return this;
    }

    public void setLoanStartDate(Instant loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public Instant getLoanEndDate() {
        return this.loanEndDate;
    }

    public LoanDetails loanEndDate(Instant loanEndDate) {
        this.setLoanEndDate(loanEndDate);
        return this;
    }

    public void setLoanEndDate(Instant loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public Instant getLoanPlannedClosureDate() {
        return this.loanPlannedClosureDate;
    }

    public LoanDetails loanPlannedClosureDate(Instant loanPlannedClosureDate) {
        this.setLoanPlannedClosureDate(loanPlannedClosureDate);
        return this;
    }

    public void setLoanPlannedClosureDate(Instant loanPlannedClosureDate) {
        this.loanPlannedClosureDate = loanPlannedClosureDate;
    }

    public Instant getLoanCloserDate() {
        return this.loanCloserDate;
    }

    public LoanDetails loanCloserDate(Instant loanCloserDate) {
        this.setLoanCloserDate(loanCloserDate);
        return this;
    }

    public void setLoanCloserDate(Instant loanCloserDate) {
        this.loanCloserDate = loanCloserDate;
    }

    public Instant getLoanEffectiveDate() {
        return this.loanEffectiveDate;
    }

    public LoanDetails loanEffectiveDate(Instant loanEffectiveDate) {
        this.setLoanEffectiveDate(loanEffectiveDate);
        return this;
    }

    public void setLoanEffectiveDate(Instant loanEffectiveDate) {
        this.loanEffectiveDate = loanEffectiveDate;
    }

    public NpaClassification getLoanClassification() {
        return this.loanClassification;
    }

    public LoanDetails loanClassification(NpaClassification loanClassification) {
        this.setLoanClassification(loanClassification);
        return this;
    }

    public void setLoanClassification(NpaClassification loanClassification) {
        this.loanClassification = loanClassification;
    }

    public String getResolutionNo() {
        return this.resolutionNo;
    }

    public LoanDetails resolutionNo(String resolutionNo) {
        this.setResolutionNo(resolutionNo);
        return this;
    }

    public void setResolutionNo(String resolutionNo) {
        this.resolutionNo = resolutionNo;
    }

    public Instant getResolutionDate() {
        return this.resolutionDate;
    }

    public LoanDetails resolutionDate(Instant resolutionDate) {
        this.setResolutionDate(resolutionDate);
        return this;
    }

    public void setResolutionDate(Instant resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public Double getCostOfInvestment() {
        return this.costOfInvestment;
    }

    public LoanDetails costOfInvestment(Double costOfInvestment) {
        this.setCostOfInvestment(costOfInvestment);
        return this;
    }

    public void setCostOfInvestment(Double costOfInvestment) {
        this.costOfInvestment = costOfInvestment;
    }

    public Double getLoanBenefitingArea() {
        return this.loanBenefitingArea;
    }

    public LoanDetails loanBenefitingArea(Double loanBenefitingArea) {
        this.setLoanBenefitingArea(loanBenefitingArea);
        return this;
    }

    public void setLoanBenefitingArea(Double loanBenefitingArea) {
        this.loanBenefitingArea = loanBenefitingArea;
    }

    public Long getDccbLoanNo() {
        return this.dccbLoanNo;
    }

    public LoanDetails dccbLoanNo(Long dccbLoanNo) {
        this.setDccbLoanNo(dccbLoanNo);
        return this;
    }

    public void setDccbLoanNo(Long dccbLoanNo) {
        this.dccbLoanNo = dccbLoanNo;
    }

    public Long getMortgageDeedNo() {
        return this.mortgageDeedNo;
    }

    public LoanDetails mortgageDeedNo(Long mortgageDeedNo) {
        this.setMortgageDeedNo(mortgageDeedNo);
        return this;
    }

    public void setMortgageDeedNo(Long mortgageDeedNo) {
        this.mortgageDeedNo = mortgageDeedNo;
    }

    public Instant getMortgageDate() {
        return this.mortgageDate;
    }

    public LoanDetails mortgageDate(Instant mortgageDate) {
        this.setMortgageDate(mortgageDate);
        return this;
    }

    public void setMortgageDate(Instant mortgageDate) {
        this.mortgageDate = mortgageDate;
    }

    public Double getExtentMorgageValue() {
        return this.extentMorgageValue;
    }

    public LoanDetails extentMorgageValue(Double extentMorgageValue) {
        this.setExtentMorgageValue(extentMorgageValue);
        return this;
    }

    public void setExtentMorgageValue(Double extentMorgageValue) {
        this.extentMorgageValue = extentMorgageValue;
    }

    public String getParentAccHeadCode() {
        return this.parentAccHeadCode;
    }

    public LoanDetails parentAccHeadCode(String parentAccHeadCode) {
        this.setParentAccHeadCode(parentAccHeadCode);
        return this;
    }

    public void setParentAccHeadCode(String parentAccHeadCode) {
        this.parentAccHeadCode = parentAccHeadCode;
    }

    public String getLoanAccountName() {
        return this.loanAccountName;
    }

    public LoanDetails loanAccountName(String loanAccountName) {
        this.setLoanAccountName(loanAccountName);
        return this;
    }

    public void setLoanAccountName(String loanAccountName) {
        this.loanAccountName = loanAccountName;
    }

    public Double getDisbursementAmt() {
        return this.disbursementAmt;
    }

    public LoanDetails disbursementAmt(Double disbursementAmt) {
        this.setDisbursementAmt(disbursementAmt);
        return this;
    }

    public void setDisbursementAmt(Double disbursementAmt) {
        this.disbursementAmt = disbursementAmt;
    }

    public String getDisbursementStatus() {
        return this.disbursementStatus;
    }

    public LoanDetails disbursementStatus(String disbursementStatus) {
        this.setDisbursementStatus(disbursementStatus);
        return this;
    }

    public void setDisbursementStatus(String disbursementStatus) {
        this.disbursementStatus = disbursementStatus;
    }

    public String getYear() {
        return this.year;
    }

    public LoanDetails year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public LoanDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LoanDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public LoanDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public LoanDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public LoanDetails freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public LoanDemand getLoanDemand() {
        return this.loanDemand;
    }

    public void setLoanDemand(LoanDemand loanDemand) {
        this.loanDemand = loanDemand;
    }

    public LoanDetails loanDemand(LoanDemand loanDemand) {
        this.setLoanDemand(loanDemand);
        return this;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LoanDetails member(Member member) {
        this.setMember(member);
        return this;
    }

    public LoanDemand getLoanDemand() {
        return this.loanDemand;
    }

    public void setLoanDemand(LoanDemand loanDemand) {
        this.loanDemand = loanDemand;
    }

    public LoanDetails loanDemand(LoanDemand loanDemand) {
        this.setLoanDemand(loanDemand);
        return this;
    }

    public SocietyLoanProduct getSocietyLoanProduct() {
        return this.societyLoanProduct;
    }

    public void setSocietyLoanProduct(SocietyLoanProduct societyLoanProduct) {
        this.societyLoanProduct = societyLoanProduct;
    }

    public LoanDetails societyLoanProduct(SocietyLoanProduct societyLoanProduct) {
        this.setSocietyLoanProduct(societyLoanProduct);
        return this;
    }

    public BankDhoranDetails getBankDhoranDetails() {
        return this.bankDhoranDetails;
    }

    public void setBankDhoranDetails(BankDhoranDetails bankDhoranDetails) {
        this.bankDhoranDetails = bankDhoranDetails;
    }

    public LoanDetails bankDhoranDetails(BankDhoranDetails bankDhoranDetails) {
        this.setBankDhoranDetails(bankDhoranDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanDetails)) {
            return false;
        }
        return id != null && id.equals(((LoanDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanDetails{" +
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
            "}";
    }
}
