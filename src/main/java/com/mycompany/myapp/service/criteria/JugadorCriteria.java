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
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Jugador} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.JugadorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /jugadors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class JugadorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter apodo;

    private StringFilter nombre;

    private StringFilter apellido;

    private LocalDateFilter fechaDeNacimiento;

    private LongFilter juegoId;

    private LongFilter partidaId;

    private Boolean distinct;

    public JugadorCriteria() {}

    public JugadorCriteria(JugadorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.apodo = other.apodo == null ? null : other.apodo.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.apellido = other.apellido == null ? null : other.apellido.copy();
        this.fechaDeNacimiento = other.fechaDeNacimiento == null ? null : other.fechaDeNacimiento.copy();
        this.juegoId = other.juegoId == null ? null : other.juegoId.copy();
        this.partidaId = other.partidaId == null ? null : other.partidaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public JugadorCriteria copy() {
        return new JugadorCriteria(this);
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

    public StringFilter getApodo() {
        return apodo;
    }

    public StringFilter apodo() {
        if (apodo == null) {
            apodo = new StringFilter();
        }
        return apodo;
    }

    public void setApodo(StringFilter apodo) {
        this.apodo = apodo;
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

    public StringFilter getApellido() {
        return apellido;
    }

    public StringFilter apellido() {
        if (apellido == null) {
            apellido = new StringFilter();
        }
        return apellido;
    }

    public void setApellido(StringFilter apellido) {
        this.apellido = apellido;
    }

    public LocalDateFilter getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public LocalDateFilter fechaDeNacimiento() {
        if (fechaDeNacimiento == null) {
            fechaDeNacimiento = new LocalDateFilter();
        }
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDateFilter fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
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
        final JugadorCriteria that = (JugadorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(apodo, that.apodo) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(apellido, that.apellido) &&
            Objects.equals(fechaDeNacimiento, that.fechaDeNacimiento) &&
            Objects.equals(juegoId, that.juegoId) &&
            Objects.equals(partidaId, that.partidaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apodo, nombre, apellido, fechaDeNacimiento, juegoId, partidaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JugadorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (apodo != null ? "apodo=" + apodo + ", " : "") +
            (nombre != null ? "nombre=" + nombre + ", " : "") +
            (apellido != null ? "apellido=" + apellido + ", " : "") +
            (fechaDeNacimiento != null ? "fechaDeNacimiento=" + fechaDeNacimiento + ", " : "") +
            (juegoId != null ? "juegoId=" + juegoId + ", " : "") +
            (partidaId != null ? "partidaId=" + partidaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
