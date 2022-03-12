package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Partida} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PartidaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /partidas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PartidaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ganador;

    private StringFilter perdedor;

    private IntegerFilter puntosDelGanador;

    private LongFilter juegoId;

    private LongFilter jugadorId;

    private Boolean distinct;

    public PartidaCriteria() {}

    public PartidaCriteria(PartidaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ganador = other.ganador == null ? null : other.ganador.copy();
        this.perdedor = other.perdedor == null ? null : other.perdedor.copy();
        this.puntosDelGanador = other.puntosDelGanador == null ? null : other.puntosDelGanador.copy();
        this.juegoId = other.juegoId == null ? null : other.juegoId.copy();
        this.jugadorId = other.jugadorId == null ? null : other.jugadorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PartidaCriteria copy() {
        return new PartidaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getGanador() {
        return ganador;
    }

    public StringFilter ganador() {
        if (ganador == null) {
            ganador = new StringFilter();
        }
        return ganador;
    }

    public void setGanador(StringFilter ganador) {
        this.ganador = ganador;
    }

    public StringFilter getPerdedor() {
        return perdedor;
    }

    public StringFilter perdedor() {
        if (perdedor == null) {
            perdedor = new StringFilter();
        }
        return perdedor;
    }

    public void setPerdedor(StringFilter perdedor) {
        this.perdedor = perdedor;
    }

    public IntegerFilter getPuntosDelGanador() {
        return puntosDelGanador;
    }

    public IntegerFilter puntosDelGanador() {
        if (puntosDelGanador == null) {
            puntosDelGanador = new IntegerFilter();
        }
        return puntosDelGanador;
    }

    public void setPuntosDelGanador(IntegerFilter puntosDelGanador) {
        this.puntosDelGanador = puntosDelGanador;
    }

    public LongFilter getJuegoId() {
        return juegoId;
    }

    public LongFilter juegoId() {
        if (juegoId == null) {
            juegoId = new LongFilter();
        }
        return juegoId;
    }

    public void setJuegoId(LongFilter juegoId) {
        this.juegoId = juegoId;
    }

    public LongFilter getJugadorId() {
        return jugadorId;
    }

    public LongFilter jugadorId() {
        if (jugadorId == null) {
            jugadorId = new LongFilter();
        }
        return jugadorId;
    }

    public void setJugadorId(LongFilter jugadorId) {
        this.jugadorId = jugadorId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PartidaCriteria that = (PartidaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(ganador, that.ganador) &&
            Objects.equals(perdedor, that.perdedor) &&
            Objects.equals(puntosDelGanador, that.puntosDelGanador) &&
            Objects.equals(juegoId, that.juegoId) &&
            Objects.equals(jugadorId, that.jugadorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ganador, perdedor, puntosDelGanador, juegoId, jugadorId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartidaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (ganador != null ? "ganador=" + ganador + ", " : "") +
            (perdedor != null ? "perdedor=" + perdedor + ", " : "") +
            (puntosDelGanador != null ? "puntosDelGanador=" + puntosDelGanador + ", " : "") +
            (juegoId != null ? "juegoId=" + juegoId + ", " : "") +
            (jugadorId != null ? "jugadorId=" + jugadorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
