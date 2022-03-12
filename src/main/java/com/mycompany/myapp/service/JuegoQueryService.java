package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Juego;
import com.mycompany.myapp.repository.JuegoRepository;
import com.mycompany.myapp.service.criteria.JuegoCriteria;
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
 * Service for executing complex queries for {@link Juego} entities in the database.
 * The main input is a {@link JuegoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Juego} or a {@link Page} of {@link Juego} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JuegoQueryService extends QueryService<Juego> {

    private final Logger log = LoggerFactory.getLogger(JuegoQueryService.class);

    private final JuegoRepository juegoRepository;

    public JuegoQueryService(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }

    /**
     * Return a {@link List} of {@link Juego} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Juego> findByCriteria(JuegoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Juego> specification = createSpecification(criteria);
        return juegoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Juego} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Juego> findByCriteria(JuegoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Juego> specification = createSpecification(criteria);
        return juegoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JuegoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Juego> specification = createSpecification(criteria);
        return juegoRepository.count(specification);
    }

    /**
     * Function to convert {@link JuegoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Juego> createSpecification(JuegoCriteria criteria) {
        Specification<Juego> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Juego_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Juego_.nombre));
            }
            if (criteria.getJugadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getJugadorId(), root -> root.join(Juego_.jugadors, JoinType.LEFT).get(Jugador_.id))
                    );
            }
            if (criteria.getPartidaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPartidaId(), root -> root.join(Juego_.partidas, JoinType.LEFT).get(Partida_.id))
                    );
            }
        }
        return specification;
    }
}
