package com.vgtech.vks.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.SchemesDetails} entity.
 */
public class SchemesDetailsDTO implements Serializable {

    private Long id;

    private Integer fdDurationDays;

    private Double interest;

    private Integer lockInPeriod;

    private String schemeName;

    private Integer rdDurationMonths;

    private Boolean reinvestInterest;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean isDeleted;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private SocietyDTO society;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFdDurationDays() {
        return fdDurationDays;
    }

    public void setFdDurationDays(Integer fdDurationDays) {
        this.fdDurationDays = fdDurationDays;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Integer getLockInPeriod() {
        return lockInPeriod;
    }

    public void setLockInPeriod(Integer lockInPeriod) {
        this.lockInPeriod = lockInPeriod;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public Integer getRdDurationMonths() {
        return rdDurationMonths;
    }

    public void setRdDurationMonths(Integer rdDurationMonths) {
        this.rdDurationMonths = rdDurationMonths;
    }

    public Boolean getReinvestInterest() {
        return reinvestInterest;
    }

    public void setReinvestInterest(Boolean reinvestInterest) {
        this.reinvestInterest = reinvestInterest;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public SocietyDTO getSociety() {
        return society;
    }

    public void setSociety(SocietyDTO society) {
        this.society = society;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchemesDetailsDTO)) {
            return false;
        }

        SchemesDetailsDTO schemesDetailsDTO = (SchemesDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, schemesDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchemesDetailsDTO{" +
            "id=" + getId() +
            ", fdDurationDays=" + getFdDurationDays() +
            ", interest=" + getInterest() +
            ", lockInPeriod=" + getLockInPeriod() +
            ", schemeName='" + getSchemeName() + "'" +
            ", rdDurationMonths=" + getRdDurationMonths() +
            ", reinvestInterest='" + getReinvestInterest() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", society=" + getSociety() +
            "}";
    }
}
