package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.NetworkParameter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NetworkParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NetworkParameterRepository extends JpaRepository<NetworkParameter, Long>, JpaSpecificationExecutor<NetworkParameter> {

}
