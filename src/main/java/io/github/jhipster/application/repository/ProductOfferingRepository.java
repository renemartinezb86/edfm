package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ProductOffering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ProductOffering entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductOfferingRepository extends JpaRepository<ProductOffering, Long>, JpaSpecificationExecutor<ProductOffering> {

    @Query(value = "select distinct product_offering from ProductOffering product_offering left join fetch product_offering.rules left join fetch product_offering.resources left join fetch product_offering.networkResources left join fetch product_offering.services left join fetch product_offering.cfssPops left join fetch product_offering.freeUnits",
        countQuery = "select count(distinct product_offering) from ProductOffering product_offering")
    Page<ProductOffering> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct product_offering from ProductOffering product_offering left join fetch product_offering.rules left join fetch product_offering.resources left join fetch product_offering.networkResources left join fetch product_offering.services left join fetch product_offering.cfssPops left join fetch product_offering.freeUnits")
    List<ProductOffering> findAllWithEagerRelationships();

    @Query("select product_offering from ProductOffering product_offering left join fetch product_offering.rules left join fetch product_offering.resources left join fetch product_offering.networkResources left join fetch product_offering.services left join fetch product_offering.cfssPops left join fetch product_offering.freeUnits where product_offering.id =:id")
    Optional<ProductOffering> findOneWithEagerRelationships(@Param("id") Long id);

}
