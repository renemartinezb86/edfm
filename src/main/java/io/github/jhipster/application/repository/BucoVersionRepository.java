package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.BucoVersion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BucoVersion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BucoVersionRepository extends JpaRepository<BucoVersion, Long>, JpaSpecificationExecutor<BucoVersion> {

}
