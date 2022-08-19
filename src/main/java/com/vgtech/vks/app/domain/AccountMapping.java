package com.vgtech.vks.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vgtech.vks.app.domain.enumeration.MappingType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AccountMapping.
 */
@Entity
@Table(name = "account_mapping")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MappingType type;

    @Column(name = "mapping_name")
    private String mappingName;

    @Column(name = "ledger_acc_head_code")
    private String ledgerAccHeadCode;

    @Column(name = "ledger_account_id")
    private Long ledgerAccountId;

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
    @JsonIgnoreProperties(value = { "society", "ledgerAccounts" }, allowSetters = true)
    private LedgerAccounts accountMapping;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AccountMapping id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MappingType getType() {
        return this.type;
    }

    public AccountMapping type(MappingType type) {
        this.setType(type);
        return this;
    }

    public void setType(MappingType type) {
        this.type = type;
    }

    public String getMappingName() {
        return this.mappingName;
    }

    public AccountMapping mappingName(String mappingName) {
        this.setMappingName(mappingName);
        return this;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }

    public String getLedgerAccHeadCode() {
        return this.ledgerAccHeadCode;
    }

    public AccountMapping ledgerAccHeadCode(String ledgerAccHeadCode) {
        this.setLedgerAccHeadCode(ledgerAccHeadCode);
        return this;
    }

    public void setLedgerAccHeadCode(String ledgerAccHeadCode) {
        this.ledgerAccHeadCode = ledgerAccHeadCode;
    }

    public Long getLedgerAccountId() {
        return this.ledgerAccountId;
    }

    public AccountMapping ledgerAccountId(Long ledgerAccountId) {
        this.setLedgerAccountId(ledgerAccountId);
        return this;
    }

    public void setLedgerAccountId(Long ledgerAccountId) {
        this.ledgerAccountId = ledgerAccountId;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public AccountMapping lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public AccountMapping lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public AccountMapping createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public AccountMapping createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public AccountMapping isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public AccountMapping freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public AccountMapping freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public AccountMapping freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public LedgerAccounts getAccountMapping() {
        return this.accountMapping;
    }

    public void setAccountMapping(LedgerAccounts ledgerAccounts) {
        this.accountMapping = ledgerAccounts;
    }

    public AccountMapping accountMapping(LedgerAccounts ledgerAccounts) {
        this.setAccountMapping(ledgerAccounts);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountMapping)) {
            return false;
        }
        return id != null && id.equals(((AccountMapping) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountMapping{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", mappingName='" + getMappingName() + "'" +
            ", ledgerAccHeadCode='" + getLedgerAccHeadCode() + "'" +
            ", ledgerAccountId=" + getLedgerAccountId() +
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
