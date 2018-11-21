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
 * Criteria class for the ChargingSystem entity. This class is used in ChargingSystemResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /charging-systems?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChargingSystemCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter serviceClassId;

    private StringFilter offerTemplate;

    private StringFilter characteristicName;

    private LongFilter serviceId;

    private LongFilter freeUnitId;

    public ChargingSystemCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(StringFilter serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    public StringFilter getOfferTemplate() {
        return offerTemplate;
    }

    public void setOfferTemplate(StringFilter offerTemplate) {
        this.offerTemplate = offerTemplate;
    }

    public StringFilter getCharacteristicName() {
        return characteristicName;
    }

    public void setCharacteristicName(StringFilter characteristicName) {
        this.characteristicName = characteristicName;
    }

    public LongFilter getServiceId() {
        return serviceId;
    }

    public void setServiceId(LongFilter serviceId) {
        this.serviceId = serviceId;
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
        final ChargingSystemCriteria that = (ChargingSystemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serviceClassId, that.serviceClassId) &&
            Objects.equals(offerTemplate, that.offerTemplate) &&
            Objects.equals(characteristicName, that.characteristicName) &&
            Objects.equals(serviceId, that.serviceId) &&
            Objects.equals(freeUnitId, that.freeUnitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serviceClassId,
        offerTemplate,
        characteristicName,
        serviceId,
        freeUnitId
        );
    }

    @Override
    public String toString() {
        return "ChargingSystemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serviceClassId != null ? "serviceClassId=" + serviceClassId + ", " : "") +
                (offerTemplate != null ? "offerTemplate=" + offerTemplate + ", " : "") +
                (characteristicName != null ? "characteristicName=" + characteristicName + ", " : "") +
                (serviceId != null ? "serviceId=" + serviceId + ", " : "") +
                (freeUnitId != null ? "freeUnitId=" + freeUnitId + ", " : "") +
            "}";
    }

}
