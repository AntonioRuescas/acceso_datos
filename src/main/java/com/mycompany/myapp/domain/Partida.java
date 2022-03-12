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
 * A Partida.
 */
@Entity
@Table(name = "partida")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ganador", nullable = false)
    private String ganador;

    @NotNull
    @Column(name = "perdedor", nullable = false)
    private String perdedor;

    @NotNull
    @Min(value = 0)
    @Column(name = "puntos_del_ganador", nullable = false)
    private Integer puntosDelGanador;

    @ManyToOne
    @JsonIgnoreProperties(value = { "jugadors", "partidas" }, allowSetters = true)
    private Juego juego;

    @ManyToMany(mappedBy = "partidas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "juegos", "partidas" }, allowSetters = true)
    private Set<Jugador> jugadors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Partida id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGanador() {
        return this.ganador;
    }

    public Partida ganador(String ganador) {
        this.setGanador(ganador);
        return this;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public String getPerdedor() {
        return this.perdedor;
    }

    public Partida perdedor(String perdedor) {
        this.setPerdedor(perdedor);
        return this;
    }

    public void setPerdedor(String perdedor) {
        this.perdedor = perdedor;
    }

    public Integer getPuntosDelGanador() {
        return this.puntosDelGanador;
    }

    public Partida puntosDelGanador(Integer puntosDelGanador) {
        this.setPuntosDelGanador(puntosDelGanador);
        return this;
    }

    public void setPuntosDelGanador(Integer puntosDelGanador) {
        this.puntosDelGanador = puntosDelGanador;
    }

    public Juego getJuego() {
        return this.juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public Partida juego(Juego juego) {
        this.setJuego(juego);
        return this;
    }

    public Set<Jugador> getJugadors() {
        return this.jugadors;
    }

    public void setJugadors(Set<Jugador> jugadors) {
        if (this.jugadors != null) {
            this.jugadors.forEach(i -> i.removePartida(this));
        }
        if (jugadors != null) {
            jugadors.forEach(i -> i.addPartida(this));
        }
        this.jugadors = jugadors;
    }

    public Partida jugadors(Set<Jugador> jugadors) {
        this.setJugadors(jugadors);
        return this;
    }

    public Partida addJugador(Jugador jugador) {
        this.jugadors.add(jugador);
        jugador.getPartidas().add(this);
        return this;
    }

    public Partida removeJugador(Jugador jugador) {
        this.jugadors.remove(jugador);
        jugador.getPartidas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partida)) {
            return false;
        }
        return id != null && id.equals(((Partida) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Partida{" +
            "id=" + getId() +
            ", ganador='" + getGanador() + "'" +
            ", perdedor='" + getPerdedor() + "'" +
            ", puntosDelGanador=" + getPuntosDelGanador() +
            "}";
    }
}
