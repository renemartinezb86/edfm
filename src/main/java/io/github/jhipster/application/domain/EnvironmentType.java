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
 * A EnvironmentType.
 */
@Entity
@Table(name = "environment_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EnvironmentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "environmentType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Environment> environments = new HashSet<>();
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

    public EnvironmentType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Environment> getEnvironments() {
        return environments;
    }

    public EnvironmentType environments(Set<Environment> environments) {
        this.environments = environments;
        return this;
    }

    public EnvironmentType addEnvironments(Environment environment) {
        this.environments.add(environment);
        environment.setEnvironmentType(this);
        return this;
    }

    public EnvironmentType removeEnvironments(Environment environment) {
        this.environments.remove(environment);
        environment.setEnvironmentType(null);
        return this;
    }

    public void setEnvironments(Set<Environment> environments) {
        this.environments = environments;
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
        EnvironmentType environmentType = (EnvironmentType) o;
        if (environmentType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), environmentType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnvironmentType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
