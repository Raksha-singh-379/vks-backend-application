package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vgtech.vks.app.domain.enumeration.LoanType;
import com.vgtech.vks.app.domain.enumeration.PaymentMode;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LoanDisbursementDetails.
 */
@Entity
@Table(name = "loan_disbursement_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LoanDisbursementDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "disbursement_date")
    private Instant disbursementDate;

    @Column(name = "disbursement_amount")
    private Double disbursementAmount;

    @Column(name = "disbursement_number")
    private Integer disbursementNumber;

    @Column(name = "disbursement_status")
    private String disbursementStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private PaymentMode paymentMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LoanType type;

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
    @JsonIgnoreProperties(value = { "loanDemand", "member", "loanDemand", "societyLoanProduct", "bankDhoranDetails" }, allowSetters = true)
    private LoanDetails loanDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LoanDisbursementDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDisbursementDate() {
        return this.disbursementDate;
    }

    public LoanDisbursementDetails disbursementDate(Instant disbursementDate) {
        this.setDisbursementDate(disbursementDate);
        return this;
    }

    public void setDisbursementDate(Instant disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public Double getDisbursementAmount() {
        return this.disbursementAmount;
    }

    public LoanDisbursementDetails disbursementAmount(Double disbursementAmount) {
        this.setDisbursementAmount(disbursementAmount);
        return this;
    }

    public void setDisbursementAmount(Double disbursementAmount) {
        this.disbursementAmount = disbursementAmount;
    }

    public Integer getDisbursementNumber() {
        return this.disbursementNumber;
    }

    public LoanDisbursementDetails disbursementNumber(Integer disbursementNumber) {
        this.setDisbursementNumber(disbursementNumber);
        return this;
    }

    public void setDisbursementNumber(Integer disbursementNumber) {
        this.disbursementNumber = disbursementNumber;
    }

    public String getDisbursementStatus() {
        return this.disbursementStatus;
    }

    public LoanDisbursementDetails disbursementStatus(String disbursementStatus) {
        this.setDisbursementStatus(disbursementStatus);
        return this;
    }

    public void setDisbursementStatus(String disbursementStatus) {
        this.disbursementStatus = disbursementStatus;
    }

    public PaymentMode getPaymentMode() {
        return this.paymentMode;
    }

    public LoanDisbursementDetails paymentMode(PaymentMode paymentMode) {
        this.setPaymentMode(paymentMode);
        return this;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public LoanType getType() {
        return this.type;
    }

    public LoanDisbursementDetails type(LoanType type) {
        this.setType(type);
        return this;
    }

    public void setType(LoanType type) {
        this.type = type;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public LoanDisbursementDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LoanDisbursementDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public LoanDisbursementDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public LoanDisbursementDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public LoanDisbursementDetails freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public LoanDetails getLoanDetails() {
        return this.loanDetails;
    }

    public void setLoanDetails(LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
    }

    public LoanDisbursementDetails loanDetails(LoanDetails loanDetails) {
        this.setLoanDetails(loanDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanDisbursementDetails)) {
            return false;
        }
        return id != null && id.equals(((LoanDisbursementDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanDisbursementDetails{" +
            "id=" + getId() +
            ", disbursementDate='" + getDisbursementDate() + "'" +
            ", disbursementAmount=" + getDisbursementAmount() +
            ", disbursementNumber=" + getDisbursementNumber() +
            ", disbursementStatus='" + getDisbursementStatus() + "'" +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", type='" + getType() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            "}";
    }
}
