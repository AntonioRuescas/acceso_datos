package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Juego.
 */
@Entity
@Table(name = "juego")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Juego implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "juegos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "juegos", "partidas" }, allowSetters = true)
    private Set<Jugador> jugadors = new HashSet<>();

    @OneToMany(mappedBy = "juego")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "juego", "jugadors" }, allowSetters = true)
    private Set<Partida> partidas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Juego id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Juego nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Jugador> getJugadors() {
        return this.jugadors;
    }

    public void setJugadors(Set<Jugador> jugadors) {
        if (this.jugadors != null) {
            this.jugadors.forEach(i -> i.removeJuego(this));
        }
        if (jugadors != null) {
            jugadors.forEach(i -> i.addJuego(this));
        }
        this.jugadors = jugadors;
    }

    public Juego jugadors(Set<Jugador> jugadors) {
        this.setJugadors(jugadors);
        return this;
    }

    public Juego addJugador(Jugador jugador) {
        this.jugadors.add(jugador);
        jugador.getJuegos().add(this);
        return this;
    }

    public Juego removeJugador(Jugador jugador) {
        this.jugadors.remove(jugador);
        jugador.getJuegos().remove(this);
        return this;
    }

    public Set<Partida> getPartidas() {
        return this.partidas;
    }

    public void setPartidas(Set<Partida> partidas) {
        if (this.partidas != null) {
            this.partidas.forEach(i -> i.setJuego(null));
        }
        if (partidas != null) {
            partidas.forEach(i -> i.setJuego(this));
        }
        this.partidas = partidas;
    }

    public Juego partidas(Set<Partida> partidas) {
        this.setPartidas(partidas);
        return this;
    }

    public Juego addPartida(Partida partida) {
        this.partidas.add(partida);
        partida.setJuego(this);
        return this;
    }

    public Juego removePartida(Partida partida) {
        this.partidas.remove(partida);
        partida.setJuego(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Juego)) {
            return false;
        }
        return id != null && id.equals(((Juego) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Juego{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
