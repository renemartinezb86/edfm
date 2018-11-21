package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.FreeUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FreeUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FreeUnitRepository extends JpaRepository<FreeUnit, Long>, JpaSpecificationExecutor<FreeUnit> {

}
