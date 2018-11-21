package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.NetworkResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the NetworkResource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NetworkResourceRepository extends JpaRepository<NetworkResource, Long>, JpaSpecificationExecutor<NetworkResource> {

    @Query(value = "select distinct network_resource from NetworkResource network_resource left join fetch network_resource.networkParameters",
        countQuery = "select count(distinct network_resource) from NetworkResource network_resource")
    Page<NetworkResource> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct network_resource from NetworkResource network_resource left join fetch network_resource.networkParameters")
    List<NetworkResource> findAllWithEagerRelationships();

    @Query("select network_resource from NetworkResource network_resource left join fetch network_resource.networkParameters where network_resource.id =:id")
    Optional<NetworkResource> findOneWithEagerRelationships(@Param("id") Long id);

}
