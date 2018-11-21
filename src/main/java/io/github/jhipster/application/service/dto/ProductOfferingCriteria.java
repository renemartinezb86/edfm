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
 * Criteria class for the ProductOffering entity. This class is used in ProductOfferingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /product-offerings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductOfferingCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter poId;

    private StringFilter name;

    private StringFilter comercialName;

    private LongFilter poPriceId;

    private LongFilter bucoSheetId;

    private LongFilter rulesId;

    private LongFilter resourceId;

    private LongFilter networkResourcesId;

    private LongFilter serviceId;

    private LongFilter cfssPopId;

    private LongFilter freeUnitId;

    public ProductOfferingCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPoId() {
        return poId;
    }

    public void setPoId(StringFilter poId) {
        this.poId = poId;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getComercialName() {
        return comercialName;
    }

    public void setComercialName(StringFilter comercialName) {
        this.comercialName = comercialName;
    }

    public LongFilter getPoPriceId() {
        return poPriceId;
    }

    public void setPoPriceId(LongFilter poPriceId) {
        this.poPriceId = poPriceId;
    }

    public LongFilter getBucoSheetId() {
        return bucoSheetId;
    }

    public void setBucoSheetId(LongFilter bucoSheetId) {
        this.bucoSheetId = bucoSheetId;
    }

    public LongFilter getRulesId() {
        return rulesId;
    }

    public void setRulesId(LongFilter rulesId) {
        this.rulesId = rulesId;
    }

    public LongFilter getResourceId() {
        return resourceId;
    }

    public void setResourceId(LongFilter resourceId) {
        this.resourceId = resourceId;
    }

    public LongFilter getNetworkResourcesId() {
        return networkResourcesId;
    }

    public void setNetworkResourcesId(LongFilter networkResourcesId) {
        this.networkResourcesId = networkResourcesId;
    }

    public LongFilter getServiceId() {
        return serviceId;
    }

    public void setServiceId(LongFilter serviceId) {
        this.serviceId = serviceId;
    }

    public LongFilter getCfssPopId() {
        return cfssPopId;
    }

    public void setCfssPopId(LongFilter cfssPopId) {
        this.cfssPopId = cfssPopId;
    }

    public LongFilter getFreeUnitId() {
        return freeUnitId;
    }

    public void setFreeUnitId(LongFilter freeUnitId) {
        this.freeUnitId = freeUnitId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductOfferingCriteria that = (ProductOfferingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(poId, that.poId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(comercialName, that.comercialName) &&
            Objects.equals(poPriceId, that.poPriceId) &&
            Objects.equals(bucoSheetId, that.bucoSheetId) &&
            Objects.equals(rulesId, that.rulesId) &&
            Objects.equals(resourceId, that.resourceId) &&
            Objects.equals(networkResourcesId, that.networkResourcesId) &&
            Objects.equals(serviceId, that.serviceId) &&
            Objects.equals(cfssPopId, that.cfssPopId) &&
            Objects.equals(freeUnitId, that.freeUnitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        poId,
        name,
        comercialName,
        poPriceId,
        bucoSheetId,
        rulesId,
        resourceId,
        networkResourcesId,
        serviceId,
        cfssPopId,
        freeUnitId
        );
    }

    @Override
    public String toString() {
        return "ProductOfferingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (poId != null ? "poId=" + poId + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (comercialName != null ? "comercialName=" + comercialName + ", " : "") +
                (poPriceId != null ? "poPriceId=" + poPriceId + ", " : "") +
                (bucoSheetId != null ? "bucoSheetId=" + bucoSheetId + ", " : "") +
                (rulesId != null ? "rulesId=" + rulesId + ", " : "") +
                (resourceId != null ? "resourceId=" + resourceId + ", " : "") +
                (networkResourcesId != null ? "networkResourcesId=" + networkResourcesId + ", " : "") +
                (serviceId != null ? "serviceId=" + serviceId + ", " : "") +
                (cfssPopId != null ? "cfssPopId=" + cfssPopId + ", " : "") +
                (freeUnitId != null ? "freeUnitId=" + freeUnitId + ", " : "") +
            "}";
    }

}
