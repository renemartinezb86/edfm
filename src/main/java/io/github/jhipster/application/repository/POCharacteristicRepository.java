package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.POCharacteristic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the POCharacteristic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface POCharacteristicRepository extends JpaRepository<POCharacteristic, Long>, JpaSpecificationExecutor<POCharacteristic> {

}
