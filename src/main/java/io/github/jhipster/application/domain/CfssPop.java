package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.CfssPopType;

/**
 * A CfssPop.
 */
@Entity
@Table(name = "cfss_pop")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CfssPop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "cfss_pop_type")
    private CfssPopType cfssPopType;

    @Column(name = "characteristic")
    private String characteristic;

    @Column(name = "jhi_value")
    private String value;

    @ManyToMany(mappedBy = "cfssPops")
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

    public CfssPopType getCfssPopType() {
        return cfssPopType;
    }

    public CfssPop cfssPopType(CfssPopType cfssPopType) {
        this.cfssPopType = cfssPopType;
        return this;
    }

    public void setCfssPopType(CfssPopType cfssPopType) {
        this.cfssPopType = cfssPopType;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public CfssPop characteristic(String characteristic) {
        this.characteristic = characteristic;
        return this;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getValue() {
        return value;
    }

    public CfssPop value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<ProductOffering> getProductOfferings() {
        return productOfferings;
    }

    public CfssPop productOfferings(Set<ProductOffering> productOfferings) {
        this.productOfferings = productOfferings;
        return this;
    }

    public CfssPop addProductOffering(ProductOffering productOffering) {
        this.productOfferings.add(productOffering);
        productOffering.getCfssPops().add(this);
        return this;
    }

    public CfssPop removeProductOffering(ProductOffering productOffering) {
        this.productOfferings.remove(productOffering);
        productOffering.getCfssPops().remove(this);
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
        CfssPop cfssPop = (CfssPop) o;
        if (cfssPop.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cfssPop.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CfssPop{" +
            "id=" + getId() +
            ", cfssPopType='" + getCfssPopType() + "'" +
            ", characteristic='" + getCharacteristic() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
