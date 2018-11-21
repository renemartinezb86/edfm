package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.PoPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PoPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoPriceRepository extends JpaRepository<PoPrice, Long>, JpaSpecificationExecutor<PoPrice> {

}
