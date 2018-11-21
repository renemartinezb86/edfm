package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ChargingSystem.
 */
@Entity
@Table(name = "charging_system")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChargingSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "service_class_id")
    private String serviceClassId;

    @Column(name = "offer_template")
    private String offerTemplate;

    @Column(name = "characteristic_name")
    private String characteristicName;

    @OneToOne    @JoinColumn(unique = true)
    private Service service;

    @OneToOne    @JoinColumn(unique = true)
    private FreeUnit freeUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceClassId() {
        return serviceClassId;
    }

    public ChargingSystem serviceClassId(String serviceClassId) {
        this.serviceClassId = serviceClassId;
        return this;
    }

    public void setServiceClassId(String serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    public String getOfferTemplate() {
        return offerTemplate;
    }

    public ChargingSystem offerTemplate(String offerTemplate) {
        this.offerTemplate = offerTemplate;
        return this;
    }

    public void setOfferTemplate(String offerTemplate) {
        this.offerTemplate = offerTemplate;
    }

    public String getCharacteristicName() {
        return characteristicName;
    }

    public ChargingSystem characteristicName(String characteristicName) {
        this.characteristicName = characteristicName;
        return this;
    }

    public void setCharacteristicName(String characteristicName) {
        this.characteristicName = characteristicName;
    }

    public Service getService() {
        return service;
    }

    public ChargingSystem service(Service service) {
        this.service = service;
        return this;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public FreeUnit getFreeUnit() {
        return freeUnit;
    }

    public ChargingSystem freeUnit(FreeUnit freeUnit) {
        this.freeUnit = freeUnit;
        return this;
    }

    public void setFreeUnit(FreeUnit freeUnit) {
        this.freeUnit = freeUnit;
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
        ChargingSystem chargingSystem = (ChargingSystem) o;
        if (chargingSystem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chargingSystem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChargingSystem{" +
            "id=" + getId() +
            ", serviceClassId='" + getServiceClassId() + "'" +
            ", offerTemplate='" + getOfferTemplate() + "'" +
            ", characteristicName='" + getCharacteristicName() + "'" +
            "}";
    }
}
