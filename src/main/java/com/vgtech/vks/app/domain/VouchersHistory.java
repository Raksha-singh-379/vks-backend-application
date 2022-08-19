package com.vgtech.vks.app.domain;

import com.vgtech.vks.app.domain.enumeration.VoucherCode;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VouchersHistory.
 */
@Entity
@Table(name = "vouchers_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VouchersHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "voucher_date")
    private Instant voucherDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private VoucherCode code;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VouchersHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public VouchersHistory createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public VouchersHistory createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getVoucherDate() {
        return this.voucherDate;
    }

    public VouchersHistory voucherDate(Instant voucherDate) {
        this.setVoucherDate(voucherDate);
        return this;
    }

    public void setVoucherDate(Instant voucherDate) {
        this.voucherDate = voucherDate;
    }

    public VoucherCode getCode() {
        return this.code;
    }

    public VouchersHistory code(VoucherCode code) {
        this.setCode(code);
        return this;
    }

    public void setCode(VoucherCode code) {
        this.code = code;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public VouchersHistory freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public VouchersHistory freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public VouchersHistory freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VouchersHistory)) {
            return false;
        }
        return id != null && id.equals(((VouchersHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VouchersHistory{" +
            "id=" + getId() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", code='" + getCode() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            "}";
    }
}
