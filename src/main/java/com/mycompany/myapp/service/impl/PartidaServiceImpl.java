package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Partida;
import com.mycompany.myapp.repository.PartidaRepository;
import com.mycompany.myapp.service.PartidaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Partida}.
 */
@Service
@Transactional
public class PartidaServiceImpl implements PartidaService {

    private final Logger log = LoggerFactory.getLogger(PartidaServiceImpl.class);

    private final PartidaRepository partidaRepository;

    public PartidaServiceImpl(PartidaRepository partidaRepository) {
        this.partidaRepository = partidaRepository;
    }

    @Override
    public Partida save(Partida partida) {
        log.debug("Request to save Partida : {}", partida);
        return partidaRepository.save(partida);
    }

    @Override
    public Optional<Partida> partialUpdate(Partida partida) {
        log.debug("Request to partially update Partida : {}", partida);

        return partidaRepository
            .findById(partida.getId())
            .map(existingPartida -> {
                if (partida.getGanador() != null) {
                    existingPartida.setGanador(partida.getGanador());
                }
                if (partida.getPerdedor() != null) {
                    existingPartida.setPerdedor(partida.getPerdedor());
                }
                if (partida.getPuntosDelGanador() != null) {
                    existingPartida.setPuntosDelGanador(partida.getPuntosDelGanador());
                }

                return existingPartida;
            })
            .map(partidaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Partida> findAll(Pageable pageable) {
        log.debug("Request to get all Partidas");
        return partidaRepository.findAll(pageable);
    }

    public Page<Partida> findAllWithEagerRelationships(Pageable pageable) {
        return partidaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Partida> findOne(Long id) {
        log.debug("Request to get Partida : {}", id);
        return partidaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Partida : {}", id);
        partidaRepository.deleteById(id);
    }
}
