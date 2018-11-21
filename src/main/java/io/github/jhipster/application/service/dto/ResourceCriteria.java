package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.ResourceType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Resource entity. This class is used in ResourceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /resources?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResourceCriteria implements Serializable {
    /**
     * Class for filtering ResourceType
     */
    public static class ResourceTypeFilter extends Filter<ResourceType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private ResourceTypeFilter resourceType;

    private StringFilter relatedItem;

    private StringFilter relatedItemCharacteristic;

    private LongFilter productOfferingId;

    public ResourceCriteria() {
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

    public ResourceTypeFilter getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceTypeFilter resourceType) {
        this.resourceType = resourceType;
    }

    public StringFilter getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(StringFilter relatedItem) {
        this.relatedItem = relatedItem;
    }

    public StringFilter getRelatedItemCharacteristic() {
        return relatedItemCharacteristic;
    }

    public void setRelatedItemCharacteristic(StringFilter relatedItemCharacteristic) {
        this.relatedItemCharacteristic = relatedItemCharacteristic;
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
        final ResourceCriteria that = (ResourceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(resourceType, that.resourceType) &&
            Objects.equals(relatedItem, that.relatedItem) &&
            Objects.equals(relatedItemCharacteristic, that.relatedItemCharacteristic) &&
            Objects.equals(productOfferingId, that.productOfferingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        resourceType,
        relatedItem,
        relatedItemCharacteristic,
        productOfferingId
        );
    }

    @Override
    public String toString() {
        return "ResourceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (resourceType != null ? "resourceType=" + resourceType + ", " : "") +
                (relatedItem != null ? "relatedItem=" + relatedItem + ", " : "") +
                (relatedItemCharacteristic != null ? "relatedItemCharacteristic=" + relatedItemCharacteristic + ", " : "") +
                (productOfferingId != null ? "productOfferingId=" + productOfferingId + ", " : "") +
            "}";
    }

}
