package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vgtech.vks.app.domain.enumeration.SocietyAssetsType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SocietyAssets.
 */
@Entity
@Table(name = "society_assets")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SocietyAssets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "society_assets_name")
    private String societyAssetsName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SocietyAssetsType type;

    @Column(name = "category")
    private String category;

    @Column(name = "depreciation")
    private Double depreciation;

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

    public SocietyAssets id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocietyAssetsName() {
        return this.societyAssetsName;
    }

    public SocietyAssets societyAssetsName(String societyAssetsName) {
        this.setSocietyAssetsName(societyAssetsName);
        return this;
    }

    public void setSocietyAssetsName(String societyAssetsName) {
        this.societyAssetsName = societyAssetsName;
    }

    public SocietyAssetsType getType() {
        return this.type;
    }

    public SocietyAssets type(SocietyAssetsType type) {
        this.setType(type);
        return this;
    }

    public void setType(SocietyAssetsType type) {
        this.type = type;
    }

    public String getCategory() {
        return this.category;
    }

    public SocietyAssets category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getDepreciation() {
        return this.depreciation;
    }

    public SocietyAssets depreciation(Double depreciation) {
        this.setDepreciation(depreciation);
        return this;
    }

    public void setDepreciation(Double depreciation) {
        this.depreciation = depreciation;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public SocietyAssets lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public SocietyAssets lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public SocietyAssets createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public SocietyAssets createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public SocietyAssets isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public SocietyAssets freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public SocietyAssets freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public SocietyAssets freeField3(String freeField3) {
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

    public SocietyAssets society(Society society) {
        this.setSociety(society);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocietyAssets)) {
            return false;
        }
        return id != null && id.equals(((SocietyAssets) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyAssets{" +
            "id=" + getId() +
            ", societyAssetsName='" + getSocietyAssetsName() + "'" +
            ", type='" + getType() + "'" +
            ", category='" + getCategory() + "'" +
            ", depreciation=" + getDepreciation() +
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
