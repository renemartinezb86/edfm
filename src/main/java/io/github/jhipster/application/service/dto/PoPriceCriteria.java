package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.PoPriceType;
import io.github.jhipster.application.domain.enumeration.PaymentType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the PoPrice entity. This class is used in PoPriceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /po-prices?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PoPriceCriteria implements Serializable {
    /**
     * Class for filtering PoPriceType
     */
    public static class PoPriceTypeFilter extends Filter<PoPriceType> {
    }
    /**
     * Class for filtering PaymentType
     */
    public static class PaymentTypeFilter extends Filter<PaymentType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private PoPriceTypeFilter poPriceType;

    private DoubleFilter amount;

    private PaymentTypeFilter paymentType;

    private BooleanFilter showInBill;

    private BooleanFilter payInAdvance;

    private BooleanFilter billOnSuspension;

    private BooleanFilter multiDiscount;

    private LongFilter productOfferingId;

    public PoPriceCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public PoPriceTypeFilter getPoPriceType() {
        return poPriceType;
    }

    public void setPoPriceType(PoPriceTypeFilter poPriceType) {
        this.poPriceType = poPriceType;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public PaymentTypeFilter getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeFilter paymentType) {
        this.paymentType = paymentType;
    }

    public BooleanFilter getShowInBill() {
        return showInBill;
    }

    public void setShowInBill(BooleanFilter showInBill) {
        this.showInBill = showInBill;
    }

    public BooleanFilter getPayInAdvance() {
        return payInAdvance;
    }

    public void setPayInAdvance(BooleanFilter payInAdvance) {
        this.payInAdvance = payInAdvance;
    }

    public BooleanFilter getBillOnSuspension() {
        return billOnSuspension;
    }

    public void setBillOnSuspension(BooleanFilter billOnSuspension) {
        this.billOnSuspension = billOnSuspension;
    }

    public BooleanFilter getMultiDiscount() {
        return multiDiscount;
    }

    public void setMultiDiscount(BooleanFilter multiDiscount) {
        this.multiDiscount = multiDiscount;
    }

    public LongFilter getProductOfferingId() {
        return productOfferingId;
    }

    public void setProductOfferingId(LongFilter productOfferingId) {
        this.productOfferingId = productOfferingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PoPriceCriteria that = (PoPriceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(poPriceType, that.poPriceType) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(showInBill, that.showInBill) &&
            Objects.equals(payInAdvance, that.payInAdvance) &&
            Objects.equals(billOnSuspension, that.billOnSuspension) &&
            Objects.equals(multiDiscount, that.multiDiscount) &&
            Objects.equals(productOfferingId, that.productOfferingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        poPriceType,
        amount,
        paymentType,
        showInBill,
        payInAdvance,
        billOnSuspension,
        multiDiscount,
        productOfferingId
        );
    }

    @Override
    public String toString() {
        return "PoPriceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (poPriceType != null ? "poPriceType=" + poPriceType + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
                (showInBill != null ? "showInBill=" + showInBill + ", " : "") +
                (payInAdvance != null ? "payInAdvance=" + payInAdvance + ", " : "") +
                (billOnSuspension != null ? "billOnSuspension=" + billOnSuspension + ", " : "") +
                (multiDiscount != null ? "multiDiscount=" + multiDiscount + ", " : "") +
                (productOfferingId != null ? "productOfferingId=" + productOfferingId + ", " : "") +
            "}";
    }

}
