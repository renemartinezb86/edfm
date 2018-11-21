package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.ServiceType;

/**
 * A Service.
 */
@Entity
@Table(name = "service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private ServiceType serviceType;

    @OneToOne(mappedBy = "service")
    @JsonIgnore
    private ChargingSystem chargingSystem;

    @OneToOne(mappedBy = "service")
    @JsonIgnore
    private Bscs bscs;

    @ManyToMany(mappedBy = "services")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<ProductOffering> productOfferings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Service name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public Service serviceType(ServiceType serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public ChargingSystem getChargingSystem() {
        return chargingSystem;
    }

    public Service chargingSystem(ChargingSystem chargingSystem) {
        this.chargingSystem = chargingSystem;
        return this;
    }

    public void setChargingSystem(ChargingSystem chargingSystem) {
        this.chargingSystem = chargingSystem;
    }

    public Bscs getBscs() {
        return bscs;
    }

    public Service bscs(Bscs bscs) {
        this.bscs = bscs;
        return this;
    }

    public void setBscs(Bscs bscs) {
        this.bscs = bscs;
    }

    public Set<ProductOffering> getProductOfferings() {
        return productOfferings;
    }

    public Service productOfferings(Set<ProductOffering> productOfferings) {
        this.productOfferings = productOfferings;
        return this;
    }

    public Service addProductOffering(ProductOffering productOffering) {
        this.productOfferings.add(productOffering);
        productOffering.getServices().add(this);
        return this;
    }

    public Service removeProductOffering(ProductOffering productOffering) {
        this.productOfferings.remove(productOffering);
        productOffering.getServices().remove(this);
        return this;
    }

    public void setProductOfferings(Set<ProductOffering> productOfferings) {
        this.productOfferings = productOfferings;
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
        Service service = (Service) o;
        if (service.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), service.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Service{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serviceType='" + getServiceType() + "'" +
            "}";
    }
}
