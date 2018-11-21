package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.FreeUnitType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the FreeUnit entity. This class is used in FreeUnitResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /free-units?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FreeUnitCriteria implements Serializable {
    /**
     * Class for filtering FreeUnitType
     */
    public static class FreeUnitTypeFilter extends Filter<FreeUnitType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private FreeUnitTypeFilter freeUnitType;

    private StringFilter units;

    private DoubleFilter amount;

    private LongFilter chargingSystemId;

    private LongFilter productOfferingId;

    public FreeUnitCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public FreeUnitTypeFilter getFreeUnitType() {
        return freeUnitType;
    }

    public void setFreeUnitType(FreeUnitTypeFilter freeUnitType) {
        this.freeUnitType = freeUnitType;
    }

    public StringFilter getUnits() {
        return units;
    }

    public void setUnits(StringFilter units) {
        this.units = units;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public LongFilter getChargingSystemId() {
        return chargingSystemId;
    }

    public void setChargingSystemId(LongFilter chargingSystemId) {
        this.chargingSystemId = chargingSystemId;
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
        final FreeUnitCriteria that = (FreeUnitCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(freeUnitType, that.freeUnitType) &&
            Objects.equals(units, that.units) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(chargingSystemId, that.chargingSystemId) &&
            Objects.equals(productOfferingId, that.productOfferingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        freeUnitType,
        units,
        amount,
        chargingSystemId,
        productOfferingId
        );
    }

    @Override
    public String toString() {
        return "FreeUnitCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (freeUnitType != null ? "freeUnitType=" + freeUnitType + ", " : "") +
                (units != null ? "units=" + units + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (chargingSystemId != null ? "chargingSystemId=" + chargingSystemId + ", " : "") +
                (productOfferingId != null ? "productOfferingId=" + productOfferingId + ", " : "") +
            "}";
    }

}
