package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Partida;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Partida}.
 */
public interface PartidaService {
    /**
     * Save a partida.
     *
     * @param partida the entity to save.
     * @return the persisted entity.
     */
    Partida save(Partida partida);

    /**
     * Partially updates a partida.
     *
     * @param partida the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Partida> partialUpdate(Partida partida);

    /**
     * Get all the partidas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Partida> findAll(Pageable pageable);

    /**
     * Get all the partidas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Partida> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" partida.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Partida> findOne(Long id);

    /**
     * Delete the "id" partida.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
