package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A NetworkResource.
 */
@Entity
@Table(name = "network_resource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NetworkResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "network_resource_network_parameter",
               joinColumns = @JoinColumn(name = "network_resources_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "network_parameters_id", referencedColumnName = "id"))
    private Set<NetworkParameter> networkParameters = new HashSet<>();

    @ManyToMany(mappedBy = "networkResources")
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

    public NetworkResource name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<NetworkParameter> getNetworkParameters() {
        return networkParameters;
    }

    public NetworkResource networkParameters(Set<NetworkParameter> networkParameters) {
        this.networkParameters = networkParameters;
        return this;
    }

    public NetworkResource addNetworkParameter(NetworkParameter networkParameter) {
        this.networkParameters.add(networkParameter);
        networkParameter.getNetworkResources().add(this);
        return this;
    }

    public NetworkResource removeNetworkParameter(NetworkParameter networkParameter) {
        this.networkParameters.remove(networkParameter);
        networkParameter.getNetworkResources().remove(this);
        return this;
    }

    public void setNetworkParameters(Set<NetworkParameter> networkParameters) {
        this.networkParameters = networkParameters;
    }

    public Set<ProductOffering> getProductOfferings() {
        return productOfferings;
    }

    public NetworkResource productOfferings(Set<ProductOffering> productOfferings) {
        this.productOfferings = productOfferings;
        return this;
    }

    public NetworkResource addProductOffering(ProductOffering productOffering) {
        this.productOfferings.add(productOffering);
        productOffering.getNetworkResources().add(this);
        return this;
    }

    public NetworkResource removeProductOffering(ProductOffering productOffering) {
        this.productOfferings.remove(productOffering);
        productOffering.getNetworkResources().remove(this);
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
        NetworkResource networkResource = (NetworkResource) o;
        if (networkResource.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), networkResource.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NetworkResource{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
