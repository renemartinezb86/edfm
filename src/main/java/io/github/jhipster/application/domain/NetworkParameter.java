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
 * A NetworkParameter.
 */
@Entity
@Table(name = "network_parameter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NetworkParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "jhi_value")
    private String value;

    @ManyToMany(mappedBy = "networkParameters")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<NetworkResource> networkResources = new HashSet<>();

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

    public NetworkParameter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public NetworkParameter type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public NetworkParameter value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<NetworkResource> getNetworkResources() {
        return networkResources;
    }

    public NetworkParameter networkResources(Set<NetworkResource> networkResources) {
        this.networkResources = networkResources;
        return this;
    }

    public NetworkParameter addNetworkResource(NetworkResource networkResource) {
        this.networkResources.add(networkResource);
        networkResource.getNetworkParameters().add(this);
        return this;
    }

    public NetworkParameter removeNetworkResource(NetworkResource networkResource) {
        this.networkResources.remove(networkResource);
        networkResource.getNetworkParameters().remove(this);
        return this;
    }

    public void setNetworkResources(Set<NetworkResource> networkResources) {
        this.networkResources = networkResources;
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
        NetworkParameter networkParameter = (NetworkParameter) o;
        if (networkParameter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), networkParameter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NetworkParameter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
