package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.ServiceType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Service entity. This class is used in ServiceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /services?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceCriteria implements Serializable {
    /**
     * Class for filtering ServiceType
     */
    public static class ServiceTypeFilter extends Filter<ServiceType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private ServiceTypeFilter serviceType;

    private LongFilter chargingSystemId;

    private LongFilter bscsId;

    private LongFilter productOfferingId;

    public ServiceCriteria() {
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

    public ServiceTypeFilter getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceTypeFilter serviceType) {
        this.serviceType = serviceType;
    }

    public LongFilter getChargingSystemId() {
        return chargingSystemId;
    }

    public void setChargingSystemId(LongFilter chargingSystemId) {
        this.chargingSystemId = chargingSystemId;
    }

    public LongFilter getBscsId() {
        return bscsId;
    }

    public void setBscsId(LongFilter bscsId) {
        this.bscsId = bscsId;
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
        final ServiceCriteria that = (ServiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(serviceType, that.serviceType) &&
            Objects.equals(chargingSystemId, that.chargingSystemId) &&
            Objects.equals(bscsId, that.bscsId) &&
            Objects.equals(productOfferingId, that.productOfferingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        serviceType,
        chargingSystemId,
        bscsId,
        productOfferingId
        );
    }

    @Override
    public String toString() {
        return "ServiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (serviceType != null ? "serviceType=" + serviceType + ", " : "") +
                (chargingSystemId != null ? "chargingSystemId=" + chargingSystemId + ", " : "") +
                (bscsId != null ? "bscsId=" + bscsId + ", " : "") +
                (productOfferingId != null ? "productOfferingId=" + productOfferingId + ", " : "") +
            "}";
    }

}
