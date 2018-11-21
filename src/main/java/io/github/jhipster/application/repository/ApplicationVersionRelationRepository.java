package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ApplicationVersionRelation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApplicationVersionRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationVersionRelationRepository extends JpaRepository<ApplicationVersionRelation, Long> {

}
