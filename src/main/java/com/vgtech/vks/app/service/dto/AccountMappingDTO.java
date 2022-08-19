package com.vgtech.vks.app.service.dto;

import com.vgtech.vks.app.domain.enumeration.MappingType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vgtech.vks.app.domain.AccountMapping} entity.
 */
public class AccountMappingDTO implements Serializable {

    private Long id;

    private MappingType type;

    private String mappingName;

    private String ledgerAccHeadCode;

    private Long ledgerAccountId;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean isDeleted;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private LedgerAccountsDTO accountMapping;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MappingType getType() {
        return type;
    }

    public void setType(MappingType type) {
        this.type = type;
    }

    public String getMappingName() {
        return mappingName;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }

    public String getLedgerAccHeadCode() {
        return ledgerAccHeadCode;
    }

    public void setLedgerAccHeadCode(String ledgerAccHeadCode) {
        this.ledgerAccHeadCode = ledgerAccHeadCode;
    }

    public Long getLedgerAccountId() {
        return ledgerAccountId;
    }

    public void setLedgerAccountId(Long ledgerAccountId) {
        this.ledgerAccountId = ledgerAccountId;
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

    public LedgerAccountsDTO getAccountMapping() {
        return accountMapping;
    }

    public void setAccountMapping(LedgerAccountsDTO accountMapping) {
        this.accountMapping = accountMapping;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountMappingDTO)) {
            return false;
        }

        AccountMappingDTO accountMappingDTO = (AccountMappingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, accountMappingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountMappingDTO{" +
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
            ", accountMapping=" + getAccountMapping() +
            "}";
    }
}
