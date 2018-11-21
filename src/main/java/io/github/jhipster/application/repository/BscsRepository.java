package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Bscs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bscs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BscsRepository extends JpaRepository<Bscs, Long>, JpaSpecificationExecutor<Bscs> {

}
