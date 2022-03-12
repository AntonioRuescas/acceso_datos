package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Partida;
import com.mycompany.myapp.repository.PartidaRepository;
import com.mycompany.myapp.service.PartidaQueryService;
import com.mycompany.myapp.service.PartidaService;
import com.mycompany.myapp.service.criteria.PartidaCriteria;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Partida}.
 */
@RestController
@RequestMapping("/api")
public class PartidaResource {

    private final Logger log = LoggerFactory.getLogger(PartidaResource.class);

    private static final String ENTITY_NAME = "partida";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartidaService partidaService;

    private final PartidaRepository partidaRepository;

    private final PartidaQueryService partidaQueryService;

    public PartidaResource(PartidaService partidaService, PartidaRepository partidaRepository, PartidaQueryService partidaQueryService) {
        this.partidaService = partidaService;
        this.partidaRepository = partidaRepository;
        this.partidaQueryService = partidaQueryService;
    }

    /**
     * {@code POST  /partidas} : Create a new partida.
     *
     * @param partida the partida to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partida, or with status {@code 400 (Bad Request)} if the partida has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/partidas")
    public ResponseEntity<Partida> createPartida(@Valid @RequestBody Partida partida) throws URISyntaxException {
        log.debug("REST request to save Partida : {}", partida);
        if (partida.getId() != null) {
            throw new BadRequestAlertException("A new partida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Partida result = partidaService.save(partida);
        return ResponseEntity
            .created(new URI("/api/partidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /partidas/:id} : Updates an existing partida.
     *
     * @param id the id of the partida to save.
     * @param partida the partida to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partida,
     * or with status {@code 400 (Bad Request)} if the partida is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partida couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/partidas/{id}")
    public ResponseEntity<Partida> updatePartida(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Partida partida
    ) throws URISyntaxException {
        log.debug("REST request to update Partida : {}, {}", id, partida);
        if (partida.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partida.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partidaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Partida result = partidaService.save(partida);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partida.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /partidas/:id} : Partial updates given fields of an existing partida, field will ignore if it is null
     *
     * @param id the id of the partida to save.
     * @param partida the partida to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partida,
     * or with status {@code 400 (Bad Request)} if the partida is not valid,
     * or with status {@code 404 (Not Found)} if the partida is not found,
     * or with status {@code 500 (Internal Server Error)} if the partida couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/partidas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Partida> partialUpdatePartida(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Partida partida
    ) throws URISyntaxException {
        log.debug("REST request to partial update Partida partially : {}, {}", id, partida);
        if (partida.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partida.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partidaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Partida> result = partidaService.partialUpdate(partida);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partida.getId().toString())
        );
    }

    /**
     * {@code GET  /partidas} : get all the partidas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partidas in body.
     */
    @GetMapping("/partidas")
    public ResponseEntity<List<Partida>> getAllPartidas(
        PartidaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Partidas by criteria: {}", criteria);
        Page<Partida> page = partidaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /partidas/count} : count all the partidas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/partidas/count")
    public ResponseEntity<Long> countPartidas(PartidaCriteria criteria) {
        log.debug("REST request to count Partidas by criteria: {}", criteria);
        return ResponseEntity.ok().body(partidaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /partidas/:id} : get the "id" partida.
     *
     * @param id the id of the partida to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partida, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/partidas/{id}")
    public ResponseEntity<Partida> getPartida(@PathVariable Long id) {
        log.debug("REST request to get Partida : {}", id);
        Optional<Partida> partida = partidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partida);
    }

    /**
     * {@code DELETE  /partidas/:id} : delete the "id" partida.
     *
     * @param id the id of the partida to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/partidas/{id}")
    public ResponseEntity<Void> deletePartida(@PathVariable Long id) {
        log.debug("REST request to delete Partida : {}", id);
        partidaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
