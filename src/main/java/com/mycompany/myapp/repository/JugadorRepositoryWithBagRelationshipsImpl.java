package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Jugador;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class JugadorRepositoryWithBagRelationshipsImpl implements JugadorRepositoryWithBagRelationships {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<Jugador> fetchBagRelationships(Optional<Jugador> jugador) {
        return jugador.map(this::fetchJuegos).map(this::fetchPartidas);
    }

    @Override
    public Page<Jugador> fetchBagRelationships(Page<Jugador> jugadors) {
        return new PageImpl<>(fetchBagRelationships(jugadors.getContent()), jugadors.getPageable(), jugadors.getTotalElements());
    }

    @Override
    public List<Jugador> fetchBagRelationships(List<Jugador> jugadors) {
        return Optional.of(jugadors).map(this::fetchJuegos).map(this::fetchPartidas).get();
    }

    Jugador fetchJuegos(Jugador result) {
        return entityManager
            .createQuery("select jugador from Jugador jugador left join fetch jugador.juegos where jugador is :jugador", Jugador.class)
            .setParameter("jugador", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Jugador> fetchJuegos(List<Jugador> jugadors) {
        return entityManager
            .createQuery(
                "select distinct jugador from Jugador jugador left join fetch jugador.juegos where jugador in :jugadors",
                Jugador.class
            )
            .setParameter("jugadors", jugadors)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

    Jugador fetchPartidas(Jugador result) {
        return entityManager
            .createQuery("select jugador from Jugador jugador left join fetch jugador.partidas where jugador is :jugador", Jugador.class)
            .setParameter("jugador", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Jugador> fetchPartidas(List<Jugador> jugadors) {
        return entityManager
            .createQuery(
                "select distinct jugador from Jugador jugador left join fetch jugador.partidas where jugador in :jugadors",
                Jugador.class
            )
            .setParameter("jugadors", jugadors)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
