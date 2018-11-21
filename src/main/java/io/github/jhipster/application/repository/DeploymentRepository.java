package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Deployment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Deployment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeploymentRepository extends JpaRepository<Deployment, Long>, JpaSpecificationExecutor<Deployment> {

}
