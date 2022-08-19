package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GRInterestDetails.
 */
@Entity
@Table(name = "gr_interest_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GRInterestDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "loan_gr_name")
    private String loanGrName;

    @Column(name = "criteria")
    private String criteria;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "is_activated")
    private Boolean isActivated;

    @Column(name = "borrowing_interest_rate")
    private Double borrowingInterestRate;

    @Column(name = "interest_on_loan")
    private Double interestOnLoan;

    @Column(name = "penalty_interest")
    private Double penaltyInterest;

    @Column(name = "surcharge")
    private Double surcharge;

    @Column(name = "loan_duration")
    private Double loanDuration;

    @Column(name = "number_of_installment")
    private Integer numberOFInstallment;

    @Column(name = "extended_interst_rate")
    private Double extendedInterstRate;

    @Column(name = "central_gov_subsidy_interest")
    private Double centralGovSubsidyInterest;

    @Column(name = "dist_bank_subsidy_interest")
    private Double distBankSubsidyInterest;

    @Column(name = "borrower_interest")
    private Double borrowerInterest;

    @Column(name = "state_gov_subsidy_interest")
    private Double stateGovSubsidyInterest;

    @Column(name = "year")
    private String year;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

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

    @Column(name = "free_field_3")
    private String freeField3;

    @ManyToOne
    @JsonIgnoreProperties(value = { "city", "state", "district", "taluka", "society" }, allowSetters = true)
    private Society society;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GRInterestDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanGrName() {
        return this.loanGrName;
    }

    public GRInterestDetails loanGrName(String loanGrName) {
        this.setLoanGrName(loanGrName);
        return this;
    }

    public void setLoanGrName(String loanGrName) {
        this.loanGrName = loanGrName;
    }

    public String getCriteria() {
        return this.criteria;
    }

    public GRInterestDetails criteria(String criteria) {
        this.setCriteria(criteria);
        return this;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getProductType() {
        return this.productType;
    }

    public GRInterestDetails productType(String productType) {
        this.setProductType(productType);
        return this;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Boolean getIsActivated() {
        return this.isActivated;
    }

    public GRInterestDetails isActivated(Boolean isActivated) {
        this.setIsActivated(isActivated);
        return this;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public Double getBorrowingInterestRate() {
        return this.borrowingInterestRate;
    }

    public GRInterestDetails borrowingInterestRate(Double borrowingInterestRate) {
        this.setBorrowingInterestRate(borrowingInterestRate);
        return this;
    }

    public void setBorrowingInterestRate(Double borrowingInterestRate) {
        this.borrowingInterestRate = borrowingInterestRate;
    }

    public Double getInterestOnLoan() {
        return this.interestOnLoan;
    }

    public GRInterestDetails interestOnLoan(Double interestOnLoan) {
        this.setInterestOnLoan(interestOnLoan);
        return this;
    }

    public void setInterestOnLoan(Double interestOnLoan) {
        this.interestOnLoan = interestOnLoan;
    }

    public Double getPenaltyInterest() {
        return this.penaltyInterest;
    }

    public GRInterestDetails penaltyInterest(Double penaltyInterest) {
        this.setPenaltyInterest(penaltyInterest);
        return this;
    }

    public void setPenaltyInterest(Double penaltyInterest) {
        this.penaltyInterest = penaltyInterest;
    }

    public Double getSurcharge() {
        return this.surcharge;
    }

    public GRInterestDetails surcharge(Double surcharge) {
        this.setSurcharge(surcharge);
        return this;
    }

    public void setSurcharge(Double surcharge) {
        this.surcharge = surcharge;
    }

    public Double getLoanDuration() {
        return this.loanDuration;
    }

    public GRInterestDetails loanDuration(Double loanDuration) {
        this.setLoanDuration(loanDuration);
        return this;
    }

    public void setLoanDuration(Double loanDuration) {
        this.loanDuration = loanDuration;
    }

    public Integer getNumberOFInstallment() {
        return this.numberOFInstallment;
    }

    public GRInterestDetails numberOFInstallment(Integer numberOFInstallment) {
        this.setNumberOFInstallment(numberOFInstallment);
        return this;
    }

    public void setNumberOFInstallment(Integer numberOFInstallment) {
        this.numberOFInstallment = numberOFInstallment;
    }

    public Double getExtendedInterstRate() {
        return this.extendedInterstRate;
    }

    public GRInterestDetails extendedInterstRate(Double extendedInterstRate) {
        this.setExtendedInterstRate(extendedInterstRate);
        return this;
    }

    public void setExtendedInterstRate(Double extendedInterstRate) {
        this.extendedInterstRate = extendedInterstRate;
    }

    public Double getCentralGovSubsidyInterest() {
        return this.centralGovSubsidyInterest;
    }

    public GRInterestDetails centralGovSubsidyInterest(Double centralGovSubsidyInterest) {
        this.setCentralGovSubsidyInterest(centralGovSubsidyInterest);
        return this;
    }

    public void setCentralGovSubsidyInterest(Double centralGovSubsidyInterest) {
        this.centralGovSubsidyInterest = centralGovSubsidyInterest;
    }

    public Double getDistBankSubsidyInterest() {
        return this.distBankSubsidyInterest;
    }

    public GRInterestDetails distBankSubsidyInterest(Double distBankSubsidyInterest) {
        this.setDistBankSubsidyInterest(distBankSubsidyInterest);
        return this;
    }

    public void setDistBankSubsidyInterest(Double distBankSubsidyInterest) {
        this.distBankSubsidyInterest = distBankSubsidyInterest;
    }

    public Double getBorrowerInterest() {
        return this.borrowerInterest;
    }

    public GRInterestDetails borrowerInterest(Double borrowerInterest) {
        this.setBorrowerInterest(borrowerInterest);
        return this;
    }

    public void setBorrowerInterest(Double borrowerInterest) {
        this.borrowerInterest = borrowerInterest;
    }

    public Double getStateGovSubsidyInterest() {
        return this.stateGovSubsidyInterest;
    }

    public GRInterestDetails stateGovSubsidyInterest(Double stateGovSubsidyInterest) {
        this.setStateGovSubsidyInterest(stateGovSubsidyInterest);
        return this;
    }

    public void setStateGovSubsidyInterest(Double stateGovSubsidyInterest) {
        this.stateGovSubsidyInterest = stateGovSubsidyInterest;
    }

    public String getYear() {
        return this.year;
    }

    public GRInterestDetails year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public GRInterestDetails startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public GRInterestDetails endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public GRInterestDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public GRInterestDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public GRInterestDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public GRInterestDetails createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public GRInterestDetails isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public GRInterestDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public GRInterestDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public GRInterestDetails freeField3(String freeField3) {
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

    public GRInterestDetails society(Society society) {
        this.setSociety(society);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GRInterestDetails)) {
            return false;
        }
        return id != null && id.equals(((GRInterestDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GRInterestDetails{" +
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
            "}";
    }
}
