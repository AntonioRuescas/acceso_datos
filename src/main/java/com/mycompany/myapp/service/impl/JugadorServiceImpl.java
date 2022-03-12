package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Jugador;
import com.mycompany.myapp.repository.JugadorRepository;
import com.mycompany.myapp.service.JugadorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Jugador}.
 */
@Service
@Transactional
public class JugadorServiceImpl implements JugadorService {

    private final Logger log = LoggerFactory.getLogger(JugadorServiceImpl.class);

    private final JugadorRepository jugadorRepository;

    public JugadorServiceImpl(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @Override
    public Jugador save(Jugador jugador) {
        log.debug("Request to save Jugador : {}", jugador);
        return jugadorRepository.save(jugador);
    }

    @Override
    public Optional<Jugador> partialUpdate(Jugador jugador) {
        log.debug("Request to partially update Jugador : {}", jugador);

        return jugadorRepository
            .findById(jugador.getId())
            .map(existingJugador -> {
                if (jugador.getApodo() != null) {
                    existingJugador.setApodo(jugador.getApodo());
                }
                if (jugador.getNombre() != null) {
                    existingJugador.setNombre(jugador.getNombre());
                }
                if (jugador.getApellido() != null) {
                    existingJugador.setApellido(jugador.getApellido());
                }
                if (jugador.getFechaDeNacimiento() != null) {
                    existingJugador.setFechaDeNacimiento(jugador.getFechaDeNacimiento());
                }

                return existingJugador;
            })
            .map(jugadorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Jugador> findAll(Pageable pageable) {
        log.debug("Request to get all Jugadors");
        return jugadorRepository.findAll(pageable);
    }

    public Page<Jugador> findAllWithEagerRelationships(Pageable pageable) {
        return jugadorRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Jugador> findOne(Long id) {
        log.debug("Request to get Jugador : {}", id);
        return jugadorRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Jugador : {}", id);
        jugadorRepository.deleteById(id);
    }
}
