package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProductOffering.
 */
@Entity
@Table(name = "product_offering")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductOffering implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "po_id")
    private String poId;

    @Column(name = "name")
    private String name;

    @Column(name = "comercial_name")
    private String comercialName;

    @OneToOne    @JoinColumn(unique = true)
    private PoPrice poPrice;

    @ManyToOne
    @JsonIgnoreProperties("")
    private BucoSheet bucoSheet;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_offering_rules",
               joinColumns = @JoinColumn(name = "product_offerings_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "rules_id", referencedColumnName = "id"))
    private Set<Rule> rules = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_offering_resource",
               joinColumns = @JoinColumn(name = "product_offerings_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "resources_id", referencedColumnName = "id"))
    private Set<Resource> resources = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_offering_network_resources",
               joinColumns = @JoinColumn(name = "product_offerings_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "network_resources_id", referencedColumnName = "id"))
    private Set<NetworkResource> networkResources = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_offering_service",
               joinColumns = @JoinColumn(name = "product_offerings_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "services_id", referencedColumnName = "id"))
    private Set<Service> services = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_offering_cfss_pop",
               joinColumns = @JoinColumn(name = "product_offerings_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "cfss_pops_id", referencedColumnName = "id"))
    private Set<CfssPop> cfssPops = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_offering_free_unit",
               joinColumns = @JoinColumn(name = "product_offerings_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "free_units_id", referencedColumnName = "id"))
    private Set<FreeUnit> freeUnits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoId() {
        return poId;
    }

    public ProductOffering poId(String poId) {
        this.poId = poId;
        return this;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getName() {
        return name;
    }

    public ProductOffering name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComercialName() {
        return comercialName;
    }

    public ProductOffering comercialName(String comercialName) {
        this.comercialName = comercialName;
        return this;
    }

    public void setComercialName(String comercialName) {
        this.comercialName = comercialName;
    }

    public PoPrice getPoPrice() {
        return poPrice;
    }

    public ProductOffering poPrice(PoPrice poPrice) {
        this.poPrice = poPrice;
        return this;
    }

    public void setPoPrice(PoPrice poPrice) {
        this.poPrice = poPrice;
    }

    public BucoSheet getBucoSheet() {
        return bucoSheet;
    }

    public ProductOffering bucoSheet(BucoSheet bucoSheet) {
        this.bucoSheet = bucoSheet;
        return this;
    }

    public void setBucoSheet(BucoSheet bucoSheet) {
        this.bucoSheet = bucoSheet;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public ProductOffering rules(Set<Rule> rules) {
        this.rules = rules;
        return this;
    }

    public ProductOffering addRules(Rule rule) {
        this.rules.add(rule);
        rule.getProductOfferings().add(this);
        return this;
    }

    public ProductOffering removeRules(Rule rule) {
        this.rules.remove(rule);
        rule.getProductOfferings().remove(this);
        return this;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public ProductOffering resources(Set<Resource> resources) {
        this.resources = resources;
        return this;
    }

    public ProductOffering addResource(Resource resource) {
        this.resources.add(resource);
        resource.getProductOfferings().add(this);
        return this;
    }

    public ProductOffering removeResource(Resource resource) {
        this.resources.remove(resource);
        resource.getProductOfferings().remove(this);
        return this;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public Set<NetworkResource> getNetworkResources() {
        return networkResources;
    }

    public ProductOffering networkResources(Set<NetworkResource> networkResources) {
        this.networkResources = networkResources;
        return this;
    }

    public ProductOffering addNetworkResources(NetworkResource networkResource) {
        this.networkResources.add(networkResource);
        networkResource.getProductOfferings().add(this);
        return this;
    }

    public ProductOffering removeNetworkResources(NetworkResource networkResource) {
        this.networkResources.remove(networkResource);
        networkResource.getProductOfferings().remove(this);
        return this;
    }

    public void setNetworkResources(Set<NetworkResource> networkResources) {
        this.networkResources = networkResources;
    }

    public Set<Service> getServices() {
        return services;
    }

    public ProductOffering services(Set<Service> services) {
        this.services = services;
        return this;
    }

    public ProductOffering addService(Service service) {
        this.services.add(service);
        service.getProductOfferings().add(this);
        return this;
    }

    public ProductOffering removeService(Service service) {
        this.services.remove(service);
        service.getProductOfferings().remove(this);
        return this;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    public Set<CfssPop> getCfssPops() {
        return cfssPops;
    }

    public ProductOffering cfssPops(Set<CfssPop> cfssPops) {
        this.cfssPops = cfssPops;
        return this;
    }

    public ProductOffering addCfssPop(CfssPop cfssPop) {
        this.cfssPops.add(cfssPop);
        cfssPop.getProductOfferings().add(this);
        return this;
    }

    public ProductOffering removeCfssPop(CfssPop cfssPop) {
        this.cfssPops.remove(cfssPop);
        cfssPop.getProductOfferings().remove(this);
        return this;
    }

    public void setCfssPops(Set<CfssPop> cfssPops) {
        this.cfssPops = cfssPops;
    }

    public Set<FreeUnit> getFreeUnits() {
        return freeUnits;
    }

    public ProductOffering freeUnits(Set<FreeUnit> freeUnits) {
        this.freeUnits = freeUnits;
        return this;
    }

    public ProductOffering addFreeUnit(FreeUnit freeUnit) {
        this.freeUnits.add(freeUnit);
        freeUnit.getProductOfferings().add(this);
        return this;
    }

    public ProductOffering removeFreeUnit(FreeUnit freeUnit) {
        this.freeUnits.remove(freeUnit);
        freeUnit.getProductOfferings().remove(this);
        return this;
    }

    public void setFreeUnits(Set<FreeUnit> freeUnits) {
        this.freeUnits = freeUnits;
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
        ProductOffering productOffering = (ProductOffering) o;
        if (productOffering.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productOffering.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductOffering{" +
            "id=" + getId() +
            ", poId='" + getPoId() + "'" +
            ", name='" + getName() + "'" +
            ", comercialName='" + getComercialName() + "'" +
            "}";
    }
}
