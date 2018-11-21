package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.PoPriceType;

import io.github.jhipster.application.domain.enumeration.PaymentType;

/**
 * A PoPrice.
 */
@Entity
@Table(name = "po_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PoPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "po_price_type")
    private PoPriceType poPriceType;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "show_in_bill")
    private Boolean showInBill;

    @Column(name = "pay_in_advance")
    private Boolean payInAdvance;

    @Column(name = "bill_on_suspension")
    private Boolean billOnSuspension;

    @Column(name = "multi_discount")
    private Boolean multiDiscount;

    @OneToOne(mappedBy = "poPrice")
    @JsonIgnore
    private ProductOffering productOffering;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PoPriceType getPoPriceType() {
        return poPriceType;
    }

    public PoPrice poPriceType(PoPriceType poPriceType) {
        this.poPriceType = poPriceType;
        return this;
    }

    public void setPoPriceType(PoPriceType poPriceType) {
        this.poPriceType = poPriceType;
    }

    public Double getAmount() {
        return amount;
    }

    public PoPrice amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public PoPrice paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean isShowInBill() {
        return showInBill;
    }

    public PoPrice showInBill(Boolean showInBill) {
        this.showInBill = showInBill;
        return this;
    }

    public void setShowInBill(Boolean showInBill) {
        this.showInBill = showInBill;
    }

    public Boolean isPayInAdvance() {
        return payInAdvance;
    }

    public PoPrice payInAdvance(Boolean payInAdvance) {
        this.payInAdvance = payInAdvance;
        return this;
    }

    public void setPayInAdvance(Boolean payInAdvance) {
        this.payInAdvance = payInAdvance;
    }

    public Boolean isBillOnSuspension() {
        return billOnSuspension;
    }

    public PoPrice billOnSuspension(Boolean billOnSuspension) {
        this.billOnSuspension = billOnSuspension;
        return this;
    }

    public void setBillOnSuspension(Boolean billOnSuspension) {
        this.billOnSuspension = billOnSuspension;
    }

    public Boolean isMultiDiscount() {
        return multiDiscount;
    }

    public PoPrice multiDiscount(Boolean multiDiscount) {
        this.multiDiscount = multiDiscount;
        return this;
    }

    public void setMultiDiscount(Boolean multiDiscount) {
        this.multiDiscount = multiDiscount;
    }

    public ProductOffering getProductOffering() {
        return productOffering;
    }

    public PoPrice productOffering(ProductOffering productOffering) {
        this.productOffering = productOffering;
        return this;
    }

    public void setProductOffering(ProductOffering productOffering) {
        this.productOffering = productOffering;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PoPrice poPrice = (PoPrice) o;
        if (poPrice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poPrice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PoPrice{" +
            "id=" + getId() +
            ", poPriceType='" + getPoPriceType() + "'" +
            ", amount=" + getAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", showInBill='" + isShowInBill() + "'" +
            ", payInAdvance='" + isPayInAdvance() + "'" +
            ", billOnSuspension='" + isBillOnSuspension() + "'" +
            ", multiDiscount='" + isMultiDiscount() + "'" +
            "}";
    }
}
