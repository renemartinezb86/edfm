package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ApplicationVersion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApplicationVersion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationVersionRepository extends JpaRepository<ApplicationVersion, Long> {

}
