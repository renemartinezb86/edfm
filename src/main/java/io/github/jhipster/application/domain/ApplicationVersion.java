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
 * A ApplicationVersion.
 */
@Entity
@Table(name = "application_version")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApplicationVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("applicationVersions")
    private Application application;

    @OneToMany(mappedBy = "applicationVersion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationVersionRelation> versionRelations = new HashSet<>();
    @OneToMany(mappedBy = "applicationVersion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Deployment> deployments = new HashSet<>();
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

    public ApplicationVersion name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Application getApplication() {
        return application;
    }

    public ApplicationVersion application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Set<ApplicationVersionRelation> getVersionRelations() {
        return versionRelations;
    }

    public ApplicationVersion versionRelations(Set<ApplicationVersionRelation> applicationVersionRelations) {
        this.versionRelations = applicationVersionRelations;
        return this;
    }

    public ApplicationVersion addVersionRelations(ApplicationVersionRelation applicationVersionRelation) {
        this.versionRelations.add(applicationVersionRelation);
        applicationVersionRelation.setApplicationVersion(this);
        return this;
    }

    public ApplicationVersion removeVersionRelations(ApplicationVersionRelation applicationVersionRelation) {
        this.versionRelations.remove(applicationVersionRelation);
        applicationVersionRelation.setApplicationVersion(null);
        return this;
    }

    public void setVersionRelations(Set<ApplicationVersionRelation> applicationVersionRelations) {
        this.versionRelations = applicationVersionRelations;
    }

    public Set<Deployment> getDeployments() {
        return deployments;
    }

    public ApplicationVersion deployments(Set<Deployment> deployments) {
        this.deployments = deployments;
        return this;
    }

    public ApplicationVersion addDeployments(Deployment deployment) {
        this.deployments.add(deployment);
        deployment.setApplicationVersion(this);
        return this;
    }

    public ApplicationVersion removeDeployments(Deployment deployment) {
        this.deployments.remove(deployment);
        deployment.setApplicationVersion(null);
        return this;
    }

    public void setDeployments(Set<Deployment> deployments) {
        this.deployments = deployments;
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
        ApplicationVersion applicationVersion = (ApplicationVersion) o;
        if (applicationVersion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationVersion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationVersion{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
