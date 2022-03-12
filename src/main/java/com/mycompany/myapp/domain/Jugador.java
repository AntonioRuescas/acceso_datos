package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Jugador.
 */
@Entity
@Table(name = "jugador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9_]*$")
    @Column(name = "apodo", nullable = false, unique = true)
    private String apodo;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotNull
    @Column(name = "fecha_de_nacimiento", nullable = false)
    private LocalDate fechaDeNacimiento;

    @ManyToMany
    @JoinTable(
        name = "rel_jugador__juego",
        joinColumns = @JoinColumn(name = "jugador_id"),
        inverseJoinColumns = @JoinColumn(name = "juego_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jugadors", "partidas" }, allowSetters = true)
    private Set<Juego> juegos = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_jugador__partida",
        joinColumns = @JoinColumn(name = "jugador_id"),
        inverseJoinColumns = @JoinColumn(name = "partida_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "juego", "jugadors" }, allowSetters = true)
    private Set<Partida> partidas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Jugador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApodo() {
        return this.apodo;
    }

    public Jugador apodo(String apodo) {
        this.setApodo(apodo);
        return this;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Jugador nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public Jugador apellido(String apellido) {
        this.setApellido(apellido);
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaDeNacimiento() {
        return this.fechaDeNacimiento;
    }

    public Jugador fechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.setFechaDeNacimiento(fechaDeNacimiento);
        return this;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Set<Juego> getJuegos() {
        return this.juegos;
    }

    public void setJuegos(Set<Juego> juegos) {
        this.juegos = juegos;
    }

    public Jugador juegos(Set<Juego> juegos) {
        this.setJuegos(juegos);
        return this;
    }

    public Jugador addJuego(Juego juego) {
        this.juegos.add(juego);
        juego.getJugadors().add(this);
        return this;
    }

    public Jugador removeJuego(Juego juego) {
        this.juegos.remove(juego);
        juego.getJugadors().remove(this);
        return this;
    }

    public Set<Partida> getPartidas() {
        return this.partidas;
    }

    public void setPartidas(Set<Partida> partidas) {
        this.partidas = partidas;
    }

    public Jugador partidas(Set<Partida> partidas) {
        this.setPartidas(partidas);
        return this;
    }

    public Jugador addPartida(Partida partida) {
        this.partidas.add(partida);
        partida.getJugadors().add(this);
        return this;
    }

    public Jugador removePartida(Partida partida) {
        this.partidas.remove(partida);
        partida.getJugadors().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jugador)) {
            return false;
        }
        return id != null && id.equals(((Jugador) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jugador{" +
            "id=" + getId() +
            ", apodo='" + getApodo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", fechaDeNacimiento='" + getFechaDeNacimiento() + "'" +
            "}";
    }
}
