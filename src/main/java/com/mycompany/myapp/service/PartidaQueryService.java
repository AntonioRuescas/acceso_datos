package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Partida;
import com.mycompany.myapp.repository.PartidaRepository;
import com.mycompany.myapp.service.criteria.PartidaCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Partida} entities in the database.
 * The main input is a {@link PartidaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Partida} or a {@link Page} of {@link Partida} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartidaQueryService extends QueryService<Partida> {

    private final Logger log = LoggerFactory.getLogger(PartidaQueryService.class);

    private final PartidaRepository partidaRepository;

    public PartidaQueryService(PartidaRepository partidaRepository) {
        this.partidaRepository = partidaRepository;
    }

    //Consulta 1
    List<Partida> findByJugadors_ApodoOrderByGanadorAsc(String apodo) {
        return partidaRepository.findByJugadors_ApodoOrderByGanadorAsc(apodo);
    }

    List<Partida> findByJuego_NombreOrderByGanadorAsc(String nombre) {
        return partidaRepository.findByJuego_NombreOrderByGanadorAsc(nombre);
    }

    /**
     * Return a {@link List} of {@link Partida} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Partida> findByCriteria(PartidaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Partida> specification = createSpecification(criteria);
        return partidaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Partida} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Partida> findByCriteria(PartidaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Partida> specification = createSpecification(criteria);
        return partidaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PartidaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Partida> specification = createSpecification(criteria);
        return partidaRepository.count(specification);
    }

    /**
     * Function to convert {@link PartidaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Partida> createSpecification(PartidaCriteria criteria) {
        Specification<Partida> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Partida_.id));
            }
            if (criteria.getGanador() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGanador(), Partida_.ganador));
            }
            if (criteria.getPerdedor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPerdedor(), Partida_.perdedor));
            }
            if (criteria.getPuntosDelGanador() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPuntosDelGanador(), Partida_.puntosDelGanador));
            }
            if (criteria.getJuegoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getJuegoId(), root -> root.join(Partida_.juego, JoinType.LEFT).get(Juego_.id))
                    );
            }
            if (criteria.getJugadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getJugadorId(), root -> root.join(Partida_.jugadors, JoinType.LEFT).get(Jugador_.id))
                    );
            }
        }
        return specification;
    }
}
