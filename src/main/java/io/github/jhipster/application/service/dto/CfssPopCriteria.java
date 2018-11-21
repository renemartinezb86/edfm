package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.CfssPopType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the CfssPop entity. This class is used in CfssPopResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /cfss-pops?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CfssPopCriteria implements Serializable {
    /**
     * Class for filtering CfssPopType
     */
    public static class CfssPopTypeFilter extends Filter<CfssPopType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private CfssPopTypeFilter cfssPopType;

    private StringFilter characteristic;

    private StringFilter value;

    private LongFilter productOfferingId;

    public CfssPopCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public CfssPopTypeFilter getCfssPopType() {
        return cfssPopType;
    }

    public void setCfssPopType(CfssPopTypeFilter cfssPopType) {
        this.cfssPopType = cfssPopType;
    }

    public StringFilter getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(StringFilter characteristic) {
        this.characteristic = characteristic;
    }

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
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
        final CfssPopCriteria that = (CfssPopCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cfssPopType, that.cfssPopType) &&
            Objects.equals(characteristic, that.characteristic) &&
            Objects.equals(value, that.value) &&
            Objects.equals(productOfferingId, that.productOfferingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cfssPopType,
        characteristic,
        value,
        productOfferingId
        );
    }

    @Override
    public String toString() {
        return "CfssPopCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cfssPopType != null ? "cfssPopType=" + cfssPopType + ", " : "") +
                (characteristic != null ? "characteristic=" + characteristic + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (productOfferingId != null ? "productOfferingId=" + productOfferingId + ", " : "") +
            "}";
    }

}
