package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the NetworkResource entity. This class is used in NetworkResourceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /network-resources?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NetworkResourceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter networkParameterId;

    private LongFilter productOfferingId;

    public NetworkResourceCriteria() {
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

    public LongFilter getNetworkParameterId() {
        return networkParameterId;
    }

    public void setNetworkParameterId(LongFilter networkParameterId) {
        this.networkParameterId = networkParameterId;
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
        final NetworkResourceCriteria that = (NetworkResourceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(networkParameterId, that.networkParameterId) &&
            Objects.equals(productOfferingId, that.productOfferingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        networkParameterId,
        productOfferingId
        );
    }

    @Override
    public String toString() {
        return "NetworkResourceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (networkParameterId != null ? "networkParameterId=" + networkParameterId + ", " : "") +
                (productOfferingId != null ? "productOfferingId=" + productOfferingId + ", " : "") +
            "}";
    }

}
