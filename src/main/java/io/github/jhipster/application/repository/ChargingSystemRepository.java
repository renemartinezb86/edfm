package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ChargingSystem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ChargingSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChargingSystemRepository extends JpaRepository<ChargingSystem, Long>, JpaSpecificationExecutor<ChargingSystem> {

}
