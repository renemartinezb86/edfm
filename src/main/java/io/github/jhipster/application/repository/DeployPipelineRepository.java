package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DeployPipeline;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeployPipeline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeployPipelineRepository extends JpaRepository<DeployPipeline, Long> {

}
