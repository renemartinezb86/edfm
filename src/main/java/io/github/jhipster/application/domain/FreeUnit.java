package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.FreeUnitType;

/**
 * A FreeUnit.
 */
@Entity
@Table(name = "free_unit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FreeUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "free_unit_type")
    private FreeUnitType freeUnitType;

    @Column(name = "units")
    private String units;

    @Column(name = "amount")
    private Double amount;

    @OneToOne(mappedBy = "freeUnit")
    @JsonIgnore
    private ChargingSystem chargingSystem;

    @ManyToMany(mappedBy = "freeUnits")
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

    public FreeUnit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FreeUnitType getFreeUnitType() {
        return freeUnitType;
    }

    public FreeUnit freeUnitType(FreeUnitType freeUnitType) {
        this.freeUnitType = freeUnitType;
        return this;
    }

    public void setFreeUnitType(FreeUnitType freeUnitType) {
        this.freeUnitType = freeUnitType;
    }

    public String getUnits() {
        return units;
    }

    public FreeUnit units(String units) {
        this.units = units;
        return this;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Double getAmount() {
        return amount;
    }

    public FreeUnit amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ChargingSystem getChargingSystem() {
        return chargingSystem;
    }

    public FreeUnit chargingSystem(ChargingSystem chargingSystem) {
        this.chargingSystem = chargingSystem;
        return this;
    }

    public void setChargingSystem(ChargingSystem chargingSystem) {
        this.chargingSystem = chargingSystem;
    }

    public Set<ProductOffering> getProductOfferings() {
        return productOfferings;
    }

    public FreeUnit productOfferings(Set<ProductOffering> productOfferings) {
        this.productOfferings = productOfferings;
        return this;
    }

    public FreeUnit addProductOffering(ProductOffering productOffering) {
        this.productOfferings.add(productOffering);
        productOffering.getFreeUnits().add(this);
        return this;
    }

    public FreeUnit removeProductOffering(ProductOffering productOffering) {
        this.productOfferings.remove(productOffering);
        productOffering.getFreeUnits().remove(this);
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
        FreeUnit freeUnit = (FreeUnit) o;
        if (freeUnit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), freeUnit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FreeUnit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", freeUnitType='" + getFreeUnitType() + "'" +
            ", units='" + getUnits() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
