package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Program;

/**
 * A Application.
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "program")
    private Program program;

    @Column(name = "git_url")
    private String gitURL;

    @Column(name = "git_token")
    private String gitToken;

    @Column(name = "jira_url")
    private String jiraURL;

    @Column(name = "jira_token")
    private String jiraToken;

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeployPipeline> deployPipelines = new HashSet<>();
    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationVersion> applicationVersions = new HashSet<>();
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

    public Application name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Program getProgram() {
        return program;
    }

    public Application program(Program program) {
        this.program = program;
        return this;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String getGitURL() {
        return gitURL;
    }

    public Application gitURL(String gitURL) {
        this.gitURL = gitURL;
        return this;
    }

    public void setGitURL(String gitURL) {
        this.gitURL = gitURL;
    }

    public String getGitToken() {
        return gitToken;
    }

    public Application gitToken(String gitToken) {
        this.gitToken = gitToken;
        return this;
    }

    public void setGitToken(String gitToken) {
        this.gitToken = gitToken;
    }

    public String getJiraURL() {
        return jiraURL;
    }

    public Application jiraURL(String jiraURL) {
        this.jiraURL = jiraURL;
        return this;
    }

    public void setJiraURL(String jiraURL) {
        this.jiraURL = jiraURL;
    }

    public String getJiraToken() {
        return jiraToken;
    }

    public Application jiraToken(String jiraToken) {
        this.jiraToken = jiraToken;
        return this;
    }

    public void setJiraToken(String jiraToken) {
        this.jiraToken = jiraToken;
    }

    public Set<DeployPipeline> getDeployPipelines() {
        return deployPipelines;
    }

    public Application deployPipelines(Set<DeployPipeline> deployPipelines) {
        this.deployPipelines = deployPipelines;
        return this;
    }

    public Application addDeployPipelines(DeployPipeline deployPipeline) {
        this.deployPipelines.add(deployPipeline);
        deployPipeline.setApplication(this);
        return this;
    }

    public Application removeDeployPipelines(DeployPipeline deployPipeline) {
        this.deployPipelines.remove(deployPipeline);
        deployPipeline.setApplication(null);
        return this;
    }

    public void setDeployPipelines(Set<DeployPipeline> deployPipelines) {
        this.deployPipelines = deployPipelines;
    }

    public Set<ApplicationVersion> getApplicationVersions() {
        return applicationVersions;
    }

    public Application applicationVersions(Set<ApplicationVersion> applicationVersions) {
        this.applicationVersions = applicationVersions;
        return this;
    }

    public Application addApplicationVersions(ApplicationVersion applicationVersion) {
        this.applicationVersions.add(applicationVersion);
        applicationVersion.setApplication(this);
        return this;
    }

    public Application removeApplicationVersions(ApplicationVersion applicationVersion) {
        this.applicationVersions.remove(applicationVersion);
        applicationVersion.setApplication(null);
        return this;
    }

    public void setApplicationVersions(Set<ApplicationVersion> applicationVersions) {
        this.applicationVersions = applicationVersions;
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
        Application application = (Application) o;
        if (application.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), application.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", program='" + getProgram() + "'" +
            ", gitURL='" + getGitURL() + "'" +
            ", gitToken='" + getGitToken() + "'" +
            ", jiraURL='" + getJiraURL() + "'" +
            ", jiraToken='" + getJiraToken() + "'" +
            "}";
    }
}
