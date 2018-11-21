package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.CfssPop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CfssPop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CfssPopRepository extends JpaRepository<CfssPop, Long>, JpaSpecificationExecutor<CfssPop> {

}
