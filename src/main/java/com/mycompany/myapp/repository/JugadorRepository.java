package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Jugador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Jugador entity.
 */
@Repository
public interface JugadorRepository
    extends JugadorRepositoryWithBagRelationships, JpaRepository<Jugador, Long>, JpaSpecificationExecutor<Jugador> {
    default Optional<Jugador> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Jugador> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Jugador> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    //Metrica 2
    long countByPartidas_Ganador(String ganador);
}
