package com.vgtech.vks.app.service.dto;

import com.vgtech.vks.app.domain.enumeration.LoanType;
import com.vgtech.vks.app.domain.enumeration.PaymentMode;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.LoanDisbursementDetails} entity.
 */
public class LoanDisbursementDetailsDTO implements Serializable {

    private Long id;

    private Instant disbursementDate;

    private Double disbursementAmount;

    private Integer disbursementNumber;

    private String disbursementStatus;

    private PaymentMode paymentMode;

    private LoanType type;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private LoanDetailsDTO loanDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(Instant disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public Double getDisbursementAmount() {
        return disbursementAmount;
    }

    public void setDisbursementAmount(Double disbursementAmount) {
        this.disbursementAmount = disbursementAmount;
    }

    public Integer getDisbursementNumber() {
        return disbursementNumber;
    }

    public void setDisbursementNumber(Integer disbursementNumber) {
        this.disbursementNumber = disbursementNumber;
    }

    public String getDisbursementStatus() {
        return disbursementStatus;
    }

    public void setDisbursementStatus(String disbursementStatus) {
        this.disbursementStatus = disbursementStatus;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public LoanType getType() {
        return type;
    }

    public void setType(LoanType type) {
        this.type = type;
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

    public LoanDetailsDTO getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(LoanDetailsDTO loanDetails) {
        this.loanDetails = loanDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanDisbursementDetailsDTO)) {
            return false;
        }

        LoanDisbursementDetailsDTO loanDisbursementDetailsDTO = (LoanDisbursementDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loanDisbursementDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanDisbursementDetailsDTO{" +
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
            ", loanDetails=" + getLoanDetails() +
            "}";
    }
}
