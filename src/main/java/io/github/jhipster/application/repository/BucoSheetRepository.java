package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.BucoSheet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BucoSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BucoSheetRepository extends JpaRepository<BucoSheet, Long>, JpaSpecificationExecutor<BucoSheet> {

}
