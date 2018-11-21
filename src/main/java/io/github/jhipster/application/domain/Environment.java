package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Environment.
 */
@Entity
@Table(name = "environment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Environment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "environment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeployPipeline> deployPipelines = new HashSet<>();
    @OneToMany(mappedBy = "environment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Deployment> deployments = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("environments")
    private EnvironmentType environmentType;

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

    public Environment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DeployPipeline> getDeployPipelines() {
        return deployPipelines;
    }

    public Environment deployPipelines(Set<DeployPipeline> deployPipelines) {
        this.deployPipelines = deployPipelines;
        return this;
    }

    public Environment addDeployPipelines(DeployPipeline deployPipeline) {
        this.deployPipelines.add(deployPipeline);
        deployPipeline.setEnvironment(this);
        return this;
    }

    public Environment removeDeployPipelines(DeployPipeline deployPipeline) {
        this.deployPipelines.remove(deployPipeline);
        deployPipeline.setEnvironment(null);
        return this;
    }

    public void setDeployPipelines(Set<DeployPipeline> deployPipelines) {
        this.deployPipelines = deployPipelines;
    }

    public Set<Deployment> getDeployments() {
        return deployments;
    }

    public Environment deployments(Set<Deployment> deployments) {
        this.deployments = deployments;
        return this;
    }

    public Environment addDeployments(Deployment deployment) {
        this.deployments.add(deployment);
        deployment.setEnvironment(this);
        return this;
    }

    public Environment removeDeployments(Deployment deployment) {
        this.deployments.remove(deployment);
        deployment.setEnvironment(null);
        return this;
    }

    public void setDeployments(Set<Deployment> deployments) {
        this.deployments = deployments;
    }

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }

    public Environment environmentType(EnvironmentType environmentType) {
        this.environmentType = environmentType;
        return this;
    }

    public void setEnvironmentType(EnvironmentType environmentType) {
        this.environmentType = environmentType;
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
        Environment environment = (Environment) o;
        if (environment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), environment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Environment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
