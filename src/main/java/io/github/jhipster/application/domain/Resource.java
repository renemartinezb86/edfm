package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.ResourceType;

/**
 * A Resource.
 */
@Entity
@Table(name = "resource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType resourceType;

    @Column(name = "related_item")
    private String relatedItem;

    @Column(name = "related_item_characteristic")
    private String relatedItemCharacteristic;

    @ManyToMany(mappedBy = "resources")
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

    public Resource name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public Resource resourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getRelatedItem() {
        return relatedItem;
    }

    public Resource relatedItem(String relatedItem) {
        this.relatedItem = relatedItem;
        return this;
    }

    public void setRelatedItem(String relatedItem) {
        this.relatedItem = relatedItem;
    }

    public String getRelatedItemCharacteristic() {
        return relatedItemCharacteristic;
    }

    public Resource relatedItemCharacteristic(String relatedItemCharacteristic) {
        this.relatedItemCharacteristic = relatedItemCharacteristic;
        return this;
    }

    public void setRelatedItemCharacteristic(String relatedItemCharacteristic) {
        this.relatedItemCharacteristic = relatedItemCharacteristic;
    }

    public Set<ProductOffering> getProductOfferings() {
        return productOfferings;
    }

    public Resource productOfferings(Set<ProductOffering> productOfferings) {
        this.productOfferings = productOfferings;
        return this;
    }

    public Resource addProductOffering(ProductOffering productOffering) {
        this.productOfferings.add(productOffering);
        productOffering.getResources().add(this);
        return this;
    }

    public Resource removeProductOffering(ProductOffering productOffering) {
        this.productOfferings.remove(productOffering);
        productOffering.getResources().remove(this);
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
        Resource resource = (Resource) o;
        if (resource.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resource.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", resourceType='" + getResourceType() + "'" +
            ", relatedItem='" + getRelatedItem() + "'" +
            ", relatedItemCharacteristic='" + getRelatedItemCharacteristic() + "'" +
            "}";
    }
}
