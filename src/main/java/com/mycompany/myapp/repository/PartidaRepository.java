package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Partida;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Partida entity.
 */
@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long>, JpaSpecificationExecutor<Partida> {
    default Optional<Partida> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Partida> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Partida> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct partida from Partida partida left join fetch partida.juego",
        countQuery = "select count(distinct partida) from Partida partida"
    )
    Page<Partida> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct partida from Partida partida left join fetch partida.juego")
    List<Partida> findAllWithToOneRelationships();

    @Query("select partida from Partida partida left join fetch partida.juego where partida.id =:id")
    Optional<Partida> findOneWithToOneRelationships(@Param("id") Long id);
}
