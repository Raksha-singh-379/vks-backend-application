package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SocietyLoanProduct.
 */
@Entity
@Table(name = "society_loan_product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SocietyLoanProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "acc_head_code")
    private String accHeadCode;

    @Column(name = "borrowing_interest_rate")
    private Double borrowingInterestRate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "last_date_of_repayment")
    private Instant lastDateOfRepayment;

    @Column(name = "loan_number")
    private String loanNumber;

    @Column(name = "max_loan_amt")
    private Double maxLoanAmt;

    @Column(name = "no_of_disbursement")
    private Integer noOfDisbursement;

    @Column(name = "no_of_installment")
    private Integer noOfInstallment;

    @Column(name = "parent_acc_head_code")
    private String parentAccHeadCode;

    @Column(name = "parent_acc_head_id")
    private Long parentAccHeadId;

    @Column(name = "penalty_interest")
    private Double penaltyInterest;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "resolution_date")
    private Instant resolutionDate;

    @Column(name = "resolution_no")
    private String resolutionNo;

    @Column(name = "status")
    private String status;

    @Column(name = "surcharge")
    private Double surcharge;

    @Column(name = "unit_size")
    private Long unitSize;

    @Column(name = "valid_from")
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "is_activate")
    private Boolean isActivate;

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
    @JsonIgnoreProperties(value = { "city", "state", "district", "taluka", "society" }, allowSetters = true)
    private Society society;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society" }, allowSetters = true)
    private BankDhoranDetails bankDhoranDetails;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society" }, allowSetters = true)
    private GRInterestDetails gRInterestDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SocietyLoanProduct id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public SocietyLoanProduct productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAccHeadCode() {
        return this.accHeadCode;
    }

    public SocietyLoanProduct accHeadCode(String accHeadCode) {
        this.setAccHeadCode(accHeadCode);
        return this;
    }

    public void setAccHeadCode(String accHeadCode) {
        this.accHeadCode = accHeadCode;
    }

    public Double getBorrowingInterestRate() {
        return this.borrowingInterestRate;
    }

    public SocietyLoanProduct borrowingInterestRate(Double borrowingInterestRate) {
        this.setBorrowingInterestRate(borrowingInterestRate);
        return this;
    }

    public void setBorrowingInterestRate(Double borrowingInterestRate) {
        this.borrowingInterestRate = borrowingInterestRate;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public SocietyLoanProduct duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getInterestRate() {
        return this.interestRate;
    }

    public SocietyLoanProduct interestRate(Double interestRate) {
        this.setInterestRate(interestRate);
        return this;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Instant getLastDateOfRepayment() {
        return this.lastDateOfRepayment;
    }

    public SocietyLoanProduct lastDateOfRepayment(Instant lastDateOfRepayment) {
        this.setLastDateOfRepayment(lastDateOfRepayment);
        return this;
    }

    public void setLastDateOfRepayment(Instant lastDateOfRepayment) {
        this.lastDateOfRepayment = lastDateOfRepayment;
    }

    public String getLoanNumber() {
        return this.loanNumber;
    }

    public SocietyLoanProduct loanNumber(String loanNumber) {
        this.setLoanNumber(loanNumber);
        return this;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public Double getMaxLoanAmt() {
        return this.maxLoanAmt;
    }

    public SocietyLoanProduct maxLoanAmt(Double maxLoanAmt) {
        this.setMaxLoanAmt(maxLoanAmt);
        return this;
    }

    public void setMaxLoanAmt(Double maxLoanAmt) {
        this.maxLoanAmt = maxLoanAmt;
    }

    public Integer getNoOfDisbursement() {
        return this.noOfDisbursement;
    }

    public SocietyLoanProduct noOfDisbursement(Integer noOfDisbursement) {
        this.setNoOfDisbursement(noOfDisbursement);
        return this;
    }

    public void setNoOfDisbursement(Integer noOfDisbursement) {
        this.noOfDisbursement = noOfDisbursement;
    }

    public Integer getNoOfInstallment() {
        return this.noOfInstallment;
    }

    public SocietyLoanProduct noOfInstallment(Integer noOfInstallment) {
        this.setNoOfInstallment(noOfInstallment);
        return this;
    }

    public void setNoOfInstallment(Integer noOfInstallment) {
        this.noOfInstallment = noOfInstallment;
    }

    public String getParentAccHeadCode() {
        return this.parentAccHeadCode;
    }

    public SocietyLoanProduct parentAccHeadCode(String parentAccHeadCode) {
        this.setParentAccHeadCode(parentAccHeadCode);
        return this;
    }

    public void setParentAccHeadCode(String parentAccHeadCode) {
        this.parentAccHeadCode = parentAccHeadCode;
    }

    public Long getParentAccHeadId() {
        return this.parentAccHeadId;
    }

    public SocietyLoanProduct parentAccHeadId(Long parentAccHeadId) {
        this.setParentAccHeadId(parentAccHeadId);
        return this;
    }

    public void setParentAccHeadId(Long parentAccHeadId) {
        this.parentAccHeadId = parentAccHeadId;
    }

    public Double getPenaltyInterest() {
        return this.penaltyInterest;
    }

    public SocietyLoanProduct penaltyInterest(Double penaltyInterest) {
        this.setPenaltyInterest(penaltyInterest);
        return this;
    }

    public void setPenaltyInterest(Double penaltyInterest) {
        this.penaltyInterest = penaltyInterest;
    }

    public String getProductType() {
        return this.productType;
    }

    public SocietyLoanProduct productType(String productType) {
        this.setProductType(productType);
        return this;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Instant getResolutionDate() {
        return this.resolutionDate;
    }

    public SocietyLoanProduct resolutionDate(Instant resolutionDate) {
        this.setResolutionDate(resolutionDate);
        return this;
    }

    public void setResolutionDate(Instant resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getResolutionNo() {
        return this.resolutionNo;
    }

    public SocietyLoanProduct resolutionNo(String resolutionNo) {
        this.setResolutionNo(resolutionNo);
        return this;
    }

    public void setResolutionNo(String resolutionNo) {
        this.resolutionNo = resolutionNo;
    }

    public String getStatus() {
        return this.status;
    }

    public SocietyLoanProduct status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getSurcharge() {
        return this.surcharge;
    }

    public SocietyLoanProduct surcharge(Double surcharge) {
        this.setSurcharge(surcharge);
        return this;
    }

    public void setSurcharge(Double surcharge) {
        this.surcharge = surcharge;
    }

    public Long getUnitSize() {
        return this.unitSize;
    }

    public SocietyLoanProduct unitSize(Long unitSize) {
        this.setUnitSize(unitSize);
        return this;
    }

    public void setUnitSize(Long unitSize) {
        this.unitSize = unitSize;
    }

    public Instant getValidFrom() {
        return this.validFrom;
    }

    public SocietyLoanProduct validFrom(Instant validFrom) {
        this.setValidFrom(validFrom);
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return this.validTo;
    }

    public SocietyLoanProduct validTo(Instant validTo) {
        this.setValidTo(validTo);
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public SocietyLoanProduct createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public SocietyLoanProduct createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getIsActivate() {
        return this.isActivate;
    }

    public SocietyLoanProduct isActivate(Boolean isActivate) {
        this.setIsActivate(isActivate);
        return this;
    }

    public void setIsActivate(Boolean isActivate) {
        this.isActivate = isActivate;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public SocietyLoanProduct lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public SocietyLoanProduct lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public SocietyLoanProduct freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public SocietyLoanProduct freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public SocietyLoanProduct freeField3(String freeField3) {
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

    public SocietyLoanProduct society(Society society) {
        this.setSociety(society);
        return this;
    }

    public BankDhoranDetails getBankDhoranDetails() {
        return this.bankDhoranDetails;
    }

    public void setBankDhoranDetails(BankDhoranDetails bankDhoranDetails) {
        this.bankDhoranDetails = bankDhoranDetails;
    }

    public SocietyLoanProduct bankDhoranDetails(BankDhoranDetails bankDhoranDetails) {
        this.setBankDhoranDetails(bankDhoranDetails);
        return this;
    }

    public GRInterestDetails getGRInterestDetails() {
        return this.gRInterestDetails;
    }

    public void setGRInterestDetails(GRInterestDetails gRInterestDetails) {
        this.gRInterestDetails = gRInterestDetails;
    }

    public SocietyLoanProduct gRInterestDetails(GRInterestDetails gRInterestDetails) {
        this.setGRInterestDetails(gRInterestDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocietyLoanProduct)) {
            return false;
        }
        return id != null && id.equals(((SocietyLoanProduct) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyLoanProduct{" +
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
            "}";
    }
}
