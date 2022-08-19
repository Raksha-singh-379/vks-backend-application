package com.vgtech.vks.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.AmortizationDetails} entity.
 */
public class AmortizationDetailsDTO implements Serializable {

    private Long id;

    private Instant installmentDate;

    private Double totalOutstandingPrincipleAmt;

    private Double totalOutstandngInterestAmt;

    private Double paidPrincipleAmt;

    private Double paidInterestAmt;

    private Double balPrincipleAmt;

    private Double balInterestAmt;

    private Double loanEmiAmt;

    private Double principleEMI;

    private String paymentStatus;

    private String year;

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

    public Instant getInstallmentDate() {
        return installmentDate;
    }

    public void setInstallmentDate(Instant installmentDate) {
        this.installmentDate = installmentDate;
    }

    public Double getTotalOutstandingPrincipleAmt() {
        return totalOutstandingPrincipleAmt;
    }

    public void setTotalOutstandingPrincipleAmt(Double totalOutstandingPrincipleAmt) {
        this.totalOutstandingPrincipleAmt = totalOutstandingPrincipleAmt;
    }

    public Double getTotalOutstandngInterestAmt() {
        return totalOutstandngInterestAmt;
    }

    public void setTotalOutstandngInterestAmt(Double totalOutstandngInterestAmt) {
        this.totalOutstandngInterestAmt = totalOutstandngInterestAmt;
    }

    public Double getPaidPrincipleAmt() {
        return paidPrincipleAmt;
    }

    public void setPaidPrincipleAmt(Double paidPrincipleAmt) {
        this.paidPrincipleAmt = paidPrincipleAmt;
    }

    public Double getPaidInterestAmt() {
        return paidInterestAmt;
    }

    public void setPaidInterestAmt(Double paidInterestAmt) {
        this.paidInterestAmt = paidInterestAmt;
    }

    public Double getBalPrincipleAmt() {
        return balPrincipleAmt;
    }

    public void setBalPrincipleAmt(Double balPrincipleAmt) {
        this.balPrincipleAmt = balPrincipleAmt;
    }

    public Double getBalInterestAmt() {
        return balInterestAmt;
    }

    public void setBalInterestAmt(Double balInterestAmt) {
        this.balInterestAmt = balInterestAmt;
    }

    public Double getLoanEmiAmt() {
        return loanEmiAmt;
    }

    public void setLoanEmiAmt(Double loanEmiAmt) {
        this.loanEmiAmt = loanEmiAmt;
    }

    public Double getPrincipleEMI() {
        return principleEMI;
    }

    public void setPrincipleEMI(Double principleEMI) {
        this.principleEMI = principleEMI;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
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
        if (!(o instanceof AmortizationDetailsDTO)) {
            return false;
        }

        AmortizationDetailsDTO amortizationDetailsDTO = (AmortizationDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, amortizationDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmortizationDetailsDTO{" +
            "id=" + getId() +
            ", installmentDate='" + getInstallmentDate() + "'" +
            ", totalOutstandingPrincipleAmt=" + getTotalOutstandingPrincipleAmt() +
            ", totalOutstandngInterestAmt=" + getTotalOutstandngInterestAmt() +
            ", paidPrincipleAmt=" + getPaidPrincipleAmt() +
            ", paidInterestAmt=" + getPaidInterestAmt() +
            ", balPrincipleAmt=" + getBalPrincipleAmt() +
            ", balInterestAmt=" + getBalInterestAmt() +
            ", loanEmiAmt=" + getLoanEmiAmt() +
            ", principleEMI=" + getPrincipleEMI() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", year='" + getYear() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", loanDetails=" + getLoanDetails() +
            "}";
    }
}
