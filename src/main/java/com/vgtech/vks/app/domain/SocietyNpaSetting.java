package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vgtech.vks.app.domain.enumeration.NpaClassification;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SocietyNpaSetting.
 */
@Entity
@Table(name = "society_npa_setting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SocietyNpaSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "npa_classification")
    private NpaClassification npaClassification;

    @Column(name = "duration_start")
    private Integer durationStart;

    @Column(name = "duration_end")
    private Integer durationEnd;

    @Column(name = "provision")
    private Double provision;

    @Column(name = "year")
    private Integer year;

    @Column(name = "interest_rate")
    private Integer interestRate;

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

    public SocietyNpaSetting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NpaClassification getNpaClassification() {
        return this.npaClassification;
    }

    public SocietyNpaSetting npaClassification(NpaClassification npaClassification) {
        this.setNpaClassification(npaClassification);
        return this;
    }

    public void setNpaClassification(NpaClassification npaClassification) {
        this.npaClassification = npaClassification;
    }

    public Integer getDurationStart() {
        return this.durationStart;
    }

    public SocietyNpaSetting durationStart(Integer durationStart) {
        this.setDurationStart(durationStart);
        return this;
    }

    public void setDurationStart(Integer durationStart) {
        this.durationStart = durationStart;
    }

    public Integer getDurationEnd() {
        return this.durationEnd;
    }

    public SocietyNpaSetting durationEnd(Integer durationEnd) {
        this.setDurationEnd(durationEnd);
        return this;
    }

    public void setDurationEnd(Integer durationEnd) {
        this.durationEnd = durationEnd;
    }

    public Double getProvision() {
        return this.provision;
    }

    public SocietyNpaSetting provision(Double provision) {
        this.setProvision(provision);
        return this;
    }

    public void setProvision(Double provision) {
        this.provision = provision;
    }

    public Integer getYear() {
        return this.year;
    }

    public SocietyNpaSetting year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getInterestRate() {
        return this.interestRate;
    }

    public SocietyNpaSetting interestRate(Integer interestRate) {
        this.setInterestRate(interestRate);
        return this;
    }

    public void setInterestRate(Integer interestRate) {
        this.interestRate = interestRate;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public SocietyNpaSetting lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public SocietyNpaSetting lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public SocietyNpaSetting createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public SocietyNpaSetting createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public SocietyNpaSetting isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public SocietyNpaSetting freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public SocietyNpaSetting freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public SocietyNpaSetting freeField3(String freeField3) {
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

    public SocietyNpaSetting society(Society society) {
        this.setSociety(society);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocietyNpaSetting)) {
            return false;
        }
        return id != null && id.equals(((SocietyNpaSetting) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyNpaSetting{" +
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
            "}";
    }
}
