package com.vgtech.vks.app.service.dto;

import com.vgtech.vks.app.domain.enumeration.NpaClassification;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.SocietyNpaSetting} entity.
 */
public class SocietyNpaSettingDTO implements Serializable {

    private Long id;

    private NpaClassification npaClassification;

    private Integer durationStart;

    private Integer durationEnd;

    private Double provision;

    private Integer year;

    private Integer interestRate;

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

    public NpaClassification getNpaClassification() {
        return npaClassification;
    }

    public void setNpaClassification(NpaClassification npaClassification) {
        this.npaClassification = npaClassification;
    }

    public Integer getDurationStart() {
        return durationStart;
    }

    public void setDurationStart(Integer durationStart) {
        this.durationStart = durationStart;
    }

    public Integer getDurationEnd() {
        return durationEnd;
    }

    public void setDurationEnd(Integer durationEnd) {
        this.durationEnd = durationEnd;
    }

    public Double getProvision() {
        return provision;
    }

    public void setProvision(Double provision) {
        this.provision = provision;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Integer interestRate) {
        this.interestRate = interestRate;
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
        if (!(o instanceof SocietyNpaSettingDTO)) {
            return false;
        }

        SocietyNpaSettingDTO societyNpaSettingDTO = (SocietyNpaSettingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, societyNpaSettingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyNpaSettingDTO{" +
            "id=" + getId() +
            ", npaClassification='" + getNpaClassification() + "'" +
            ", durationStart=" + getDurationStart() +
            ", durationEnd=" + getDurationEnd() +
            ", provision=" + getProvision() +
            ", year=" + getYear() +
            ", interestRate=" + getInterestRate() +
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
