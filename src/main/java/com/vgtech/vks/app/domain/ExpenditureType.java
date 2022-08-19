package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ExpenditureType.
 */
@Entity
@Table(name = "expenditure_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExpenditureType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "expenditure_desc")
    private String expenditureDesc;

    @Column(name = "expenditure_type")
    private String expenditureType;

    @Column(name = "expenditure_category")
    private Boolean expenditureCategory;

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

    public ExpenditureType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpenditureDesc() {
        return this.expenditureDesc;
    }

    public ExpenditureType expenditureDesc(String expenditureDesc) {
        this.setExpenditureDesc(expenditureDesc);
        return this;
    }

    public void setExpenditureDesc(String expenditureDesc) {
        this.expenditureDesc = expenditureDesc;
    }

    public String getExpenditureType() {
        return this.expenditureType;
    }

    public ExpenditureType expenditureType(String expenditureType) {
        this.setExpenditureType(expenditureType);
        return this;
    }

    public void setExpenditureType(String expenditureType) {
        this.expenditureType = expenditureType;
    }

    public Boolean getExpenditureCategory() {
        return this.expenditureCategory;
    }

    public ExpenditureType expenditureCategory(Boolean expenditureCategory) {
        this.setExpenditureCategory(expenditureCategory);
        return this;
    }

    public void setExpenditureCategory(Boolean expenditureCategory) {
        this.expenditureCategory = expenditureCategory;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public ExpenditureType lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ExpenditureType lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ExpenditureType createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public ExpenditureType createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public ExpenditureType isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public ExpenditureType freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public ExpenditureType freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public ExpenditureType freeField3(String freeField3) {
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

    public ExpenditureType society(Society society) {
        this.setSociety(society);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExpenditureType)) {
            return false;
        }
        return id != null && id.equals(((ExpenditureType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpenditureType{" +
            "id=" + getId() +
            ", expenditureDesc='" + getExpenditureDesc() + "'" +
            ", expenditureType='" + getExpenditureType() + "'" +
            ", expenditureCategory='" + getExpenditureCategory() + "'" +
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
