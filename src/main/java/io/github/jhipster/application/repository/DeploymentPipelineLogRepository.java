package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DeploymentPipelineLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeploymentPipelineLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeploymentPipelineLogRepository extends JpaRepository<DeploymentPipelineLog, Long> {

}
