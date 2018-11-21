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
 * Criteria class for the Bscs entity. This class is used in BscsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /bscs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BscsCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter services;

    private LongFilter serviceId;

    public BscsCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getServices() {
        return services;
    }

    public void setServices(StringFilter services) {
        this.services = services;
    }

    public LongFilter getServiceId() {
        return serviceId;
    }

    public void setServiceId(LongFilter serviceId) {
        this.serviceId = serviceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BscsCriteria that = (BscsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(services, that.services) &&
            Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        services,
        serviceId
        );
    }

    @Override
    public String toString() {
        return "BscsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (services != null ? "services=" + services + ", " : "") +
                (serviceId != null ? "serviceId=" + serviceId + ", " : "") +
            "}";
    }

}
