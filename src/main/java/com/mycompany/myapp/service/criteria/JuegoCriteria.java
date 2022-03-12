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
 * Criteria class for the {@link com.mycompany.myapp.domain.Juego} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.JuegoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /juegos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class JuegoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private LongFilter jugadorId;

    private LongFilter partidaId;

    private Boolean distinct;

    public JuegoCriteria() {}

    public JuegoCriteria(JuegoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.jugadorId = other.jugadorId == null ? null : other.jugadorId.copy();
        this.partidaId = other.partidaId == null ? null : other.partidaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public JuegoCriteria copy() {
        return new JuegoCriteria(this);
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

    public StringFilter getNombre() {
        return nombre;
    }

    public StringFilter nombre() {
        if (nombre == null) {
            nombre = new StringFilter();
        }
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
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

    public LongFilter getPartidaId() {
        return partidaId;
    }

    public LongFilter partidaId() {
        if (partidaId == null) {
            partidaId = new LongFilter();
        }
        return partidaId;
    }

    public void setPartidaId(LongFilter partidaId) {
        this.partidaId = partidaId;
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
        final JuegoCriteria that = (JuegoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(jugadorId, that.jugadorId) &&
            Objects.equals(partidaId, that.partidaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, jugadorId, partidaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JuegoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nombre != null ? "nombre=" + nombre + ", " : "") +
            (jugadorId != null ? "jugadorId=" + jugadorId + ", " : "") +
            (partidaId != null ? "partidaId=" + partidaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
