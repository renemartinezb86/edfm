package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.EnvironmentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EnvironmentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnvironmentTypeRepository extends JpaRepository<EnvironmentType, Long> {

}
