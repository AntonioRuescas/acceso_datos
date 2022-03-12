package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Juego;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Juego}.
 */
public interface JuegoService {
    /**
     * Save a juego.
     *
     * @param juego the entity to save.
     * @return the persisted entity.
     */
    Juego save(Juego juego);

    /**
     * Partially updates a juego.
     *
     * @param juego the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Juego> partialUpdate(Juego juego);

    /**
     * Get all the juegos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Juego> findAll(Pageable pageable);

    /**
     * Get the "id" juego.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Juego> findOne(Long id);

    /**
     * Delete the "id" juego.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
