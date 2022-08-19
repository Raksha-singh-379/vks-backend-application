package com.vgtech.vks.app.service.dto;

import com.vgtech.vks.app.domain.enumeration.PaymentMode;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.LoanRepaymentDetails} entity.
 */
public class LoanRepaymentDetailsDTO implements Serializable {

    private Long id;

    private Instant repaymentDate;

    private Double totalRepaymentAmt;

    private Integer installmentNumber;

    private Double principlePaidAmt;

    private Double interestPaidAmt;

    private Double surChargeAmt;

    private Double overDueAmt;

    private Double balanceInterestAmt;

    private Double balancePrincipleAmt;

    private PaymentMode paymentMode;

    private Double foreClosureChargeAmt;

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

    public Instant getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Instant repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Double getTotalRepaymentAmt() {
        return totalRepaymentAmt;
    }

    public void setTotalRepaymentAmt(Double totalRepaymentAmt) {
        this.totalRepaymentAmt = totalRepaymentAmt;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public Double getPrinciplePaidAmt() {
        return principlePaidAmt;
    }

    public void setPrinciplePaidAmt(Double principlePaidAmt) {
        this.principlePaidAmt = principlePaidAmt;
    }

    public Double getInterestPaidAmt() {
        return interestPaidAmt;
    }

    public void setInterestPaidAmt(Double interestPaidAmt) {
        this.interestPaidAmt = interestPaidAmt;
    }

    public Double getSurChargeAmt() {
        return surChargeAmt;
    }

    public void setSurChargeAmt(Double surChargeAmt) {
        this.surChargeAmt = surChargeAmt;
    }

    public Double getOverDueAmt() {
        return overDueAmt;
    }

    public void setOverDueAmt(Double overDueAmt) {
        this.overDueAmt = overDueAmt;
    }

    public Double getBalanceInterestAmt() {
        return balanceInterestAmt;
    }

    public void setBalanceInterestAmt(Double balanceInterestAmt) {
        this.balanceInterestAmt = balanceInterestAmt;
    }

    public Double getBalancePrincipleAmt() {
        return balancePrincipleAmt;
    }

    public void setBalancePrincipleAmt(Double balancePrincipleAmt) {
        this.balancePrincipleAmt = balancePrincipleAmt;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Double getForeClosureChargeAmt() {
        return foreClosureChargeAmt;
    }

    public void setForeClosureChargeAmt(Double foreClosureChargeAmt) {
        this.foreClosureChargeAmt = foreClosureChargeAmt;
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
        if (!(o instanceof LoanRepaymentDetailsDTO)) {
            return false;
        }

        LoanRepaymentDetailsDTO loanRepaymentDetailsDTO = (LoanRepaymentDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loanRepaymentDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanRepaymentDetailsDTO{" +
            "id=" + getId() +
            ", repaymentDate='" + getRepaymentDate() + "'" +
            ", totalRepaymentAmt=" + getTotalRepaymentAmt() +
            ", installmentNumber=" + getInstallmentNumber() +
            ", principlePaidAmt=" + getPrinciplePaidAmt() +
            ", interestPaidAmt=" + getInterestPaidAmt() +
            ", surChargeAmt=" + getSurChargeAmt() +
            ", overDueAmt=" + getOverDueAmt() +
            ", balanceInterestAmt=" + getBalanceInterestAmt() +
            ", balancePrincipleAmt=" + getBalancePrincipleAmt() +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", foreClosureChargeAmt=" + getForeClosureChargeAmt() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", loanDetails=" + getLoanDetails() +
            "}";
    }
}
