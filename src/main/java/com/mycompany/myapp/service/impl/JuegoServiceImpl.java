package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Juego;
import com.mycompany.myapp.repository.JuegoRepository;
import com.mycompany.myapp.service.JuegoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Juego}.
 */
@Service
@Transactional
public class JuegoServiceImpl implements JuegoService {

    private final Logger log = LoggerFactory.getLogger(JuegoServiceImpl.class);

    private final JuegoRepository juegoRepository;

    public JuegoServiceImpl(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }

    @Override
    public Juego save(Juego juego) {
        log.debug("Request to save Juego : {}", juego);
        return juegoRepository.save(juego);
    }

    @Override
    public Optional<Juego> partialUpdate(Juego juego) {
        log.debug("Request to partially update Juego : {}", juego);

        return juegoRepository
            .findById(juego.getId())
            .map(existingJuego -> {
                if (juego.getNombre() != null) {
                    existingJuego.setNombre(juego.getNombre());
                }

                return existingJuego;
            })
            .map(juegoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Juego> findAll(Pageable pageable) {
        log.debug("Request to get all Juegos");
        return juegoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Juego> findOne(Long id) {
        log.debug("Request to get Juego : {}", id);
        return juegoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Juego : {}", id);
        juegoRepository.deleteById(id);
    }
}
