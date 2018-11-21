package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Rule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RuleRepository extends JpaRepository<Rule, Long>, JpaSpecificationExecutor<Rule> {

}
