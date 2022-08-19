package com.vgtech.vks.app.service.dto;

import com.vgtech.vks.app.domain.enumeration.LoanStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.LoanWatapDetails} entity.
 */
public class LoanWatapDetailsDTO implements Serializable {

    private Long id;

    private Instant loanWatapDate;

    private Double cropLandInHector;

    private Integer slotNumber;

    private Double loanAmount;

    private String season;

    private LoanStatus status;

    private String year;

    private Boolean isDeleted;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private LoanDemandDTO loanDemand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLoanWatapDate() {
        return loanWatapDate;
    }

    public void setLoanWatapDate(Instant loanWatapDate) {
        this.loanWatapDate = loanWatapDate;
    }

    public Double getCropLandInHector() {
        return cropLandInHector;
    }

    public void setCropLandInHector(Double cropLandInHector) {
        this.cropLandInHector = cropLandInHector;
    }

    public Integer getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(Integer slotNumber) {
        this.slotNumber = slotNumber;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanWatapDetailsDTO)) {
            return false;
        }

        LoanWatapDetailsDTO loanWatapDetailsDTO = (LoanWatapDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loanWatapDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanWatapDetailsDTO{" +
            "id=" + getId() +
            ", loanWatapDate='" + getLoanWatapDate() + "'" +
            ", cropLandInHector=" + getCropLandInHector() +
            ", slotNumber=" + getSlotNumber() +
            ", loanAmount=" + getLoanAmount() +
            ", season='" + getSeason() + "'" +
            ", status='" + getStatus() + "'" +
            ", year='" + getYear() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", loanDemand=" + getLoanDemand() +
            "}";
    }
}
