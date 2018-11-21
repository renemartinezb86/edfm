package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Deployment entity. This class is used in DeploymentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /deployments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeploymentCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private InstantFilter date;

    private LongFilter applicationVersionId;

    private LongFilter environmentId;

    private LongFilter deploymentLogsId;

    public DeploymentCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public LongFilter getApplicationVersionId() {
        return applicationVersionId;
    }

    public void setApplicationVersionId(LongFilter applicationVersionId) {
        this.applicationVersionId = applicationVersionId;
    }

    public LongFilter getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(LongFilter environmentId) {
        this.environmentId = environmentId;
    }

    public LongFilter getDeploymentLogsId() {
        return deploymentLogsId;
    }

    public void setDeploymentLogsId(LongFilter deploymentLogsId) {
        this.deploymentLogsId = deploymentLogsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeploymentCriteria that = (DeploymentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(date, that.date) &&
            Objects.equals(applicationVersionId, that.applicationVersionId) &&
            Objects.equals(environmentId, that.environmentId) &&
            Objects.equals(deploymentLogsId, that.deploymentLogsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        date,
        applicationVersionId,
        environmentId,
        deploymentLogsId
        );
    }

    @Override
    public String toString() {
        return "DeploymentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (applicationVersionId != null ? "applicationVersionId=" + applicationVersionId + ", " : "") +
                (environmentId != null ? "environmentId=" + environmentId + ", " : "") +
                (deploymentLogsId != null ? "deploymentLogsId=" + deploymentLogsId + ", " : "") +
            "}";
    }

}
