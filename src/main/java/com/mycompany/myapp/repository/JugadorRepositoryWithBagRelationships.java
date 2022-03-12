package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Jugador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface JugadorRepositoryWithBagRelationships {
    Optional<Jugador> fetchBagRelationships(Optional<Jugador> jugador);

    List<Jugador> fetchBagRelationships(List<Jugador> jugadors);

    Page<Jugador> fetchBagRelationships(Page<Jugador> jugadors);
}
