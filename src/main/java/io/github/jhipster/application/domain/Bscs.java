package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bscs.
 */
@Entity
@Table(name = "bscs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bscs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "services")
    private String services;

    @OneToOne    @JoinColumn(unique = true)
    private Service service;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServices() {
        return services;
    }

    public Bscs services(String services) {
        this.services = services;
        return this;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Service getService() {
        return service;
    }

    public Bscs service(Service service) {
        this.service = service;
        return this;
    }

    public void setService(Service service) {
        this.service = service;
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
        Bscs bscs = (Bscs) o;
        if (bscs.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bscs.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bscs{" +
            "id=" + getId() +
            ", services='" + getServices() + "'" +
            "}";
    }
}
