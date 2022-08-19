package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vgtech.vks.app.domain.enumeration.LoanStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LoanWatapDetails.
 */
@Entity
@Table(name = "loan_watap_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LoanWatapDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "loan_watap_date")
    private Instant loanWatapDate;

    @Column(name = "crop_land_in_hector")
    private Double cropLandInHector;

    @Column(name = "slot_number")
    private Integer slotNumber;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "season")
    private String season;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoanStatus status;

    @Column(name = "year")
    private String year;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

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
    @JsonIgnoreProperties(value = { "member", "societyLoanProduct", "memberLandAssets", "societyCropRegistration" }, allowSetters = true)
    private LoanDemand loanDemand;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LoanWatapDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLoanWatapDate() {
        return this.loanWatapDate;
    }

    public LoanWatapDetails loanWatapDate(Instant loanWatapDate) {
        this.setLoanWatapDate(loanWatapDate);
        return this;
    }

    public void setLoanWatapDate(Instant loanWatapDate) {
        this.loanWatapDate = loanWatapDate;
    }

    public Double getCropLandInHector() {
        return this.cropLandInHector;
    }

    public LoanWatapDetails cropLandInHector(Double cropLandInHector) {
        this.setCropLandInHector(cropLandInHector);
        return this;
    }

    public void setCropLandInHector(Double cropLandInHector) {
        this.cropLandInHector = cropLandInHector;
    }

    public Integer getSlotNumber() {
        return this.slotNumber;
    }

    public LoanWatapDetails slotNumber(Integer slotNumber) {
        this.setSlotNumber(slotNumber);
        return this;
    }

    public void setSlotNumber(Integer slotNumber) {
        this.slotNumber = slotNumber;
    }

    public Double getLoanAmount() {
        return this.loanAmount;
    }

    public LoanWatapDetails loanAmount(Double loanAmount) {
        this.setLoanAmount(loanAmount);
        return this;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getSeason() {
        return this.season;
    }

    public LoanWatapDetails season(String season) {
        this.setSeason(season);
        return this;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public LoanStatus getStatus() {
        return this.status;
    }

    public LoanWatapDetails status(LoanStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public String getYear() {
        return this.year;
    }

    public LoanWatapDetails year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public LoanWatapDetails isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public LoanWatapDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LoanWatapDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public LoanWatapDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public LoanWatapDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public LoanWatapDetails freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public LoanDemand getLoanDemand() {
        return this.loanDemand;
    }

    public void setLoanDemand(LoanDemand loanDemand) {
        this.loanDemand = loanDemand;
    }

    public LoanWatapDetails loanDemand(LoanDemand loanDemand) {
        this.setLoanDemand(loanDemand);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanWatapDetails)) {
            return false;
        }
        return id != null && id.equals(((LoanWatapDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanWatapDetails{" +
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
            "}";
    }
}
