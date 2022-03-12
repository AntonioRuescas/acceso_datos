package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Juego;
import com.mycompany.myapp.domain.Jugador;
import com.mycompany.myapp.domain.Partida;
import com.mycompany.myapp.repository.PartidaRepository;
import com.mycompany.myapp.service.criteria.PartidaCriteria;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartidaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartidaResourceIT {

    private static final String DEFAULT_GANADOR = "AAAAAAAAAA";
    private static final String UPDATED_GANADOR = "BBBBBBBBBB";

    private static final String DEFAULT_PERDEDOR = "AAAAAAAAAA";
    private static final String UPDATED_PERDEDOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUNTOS_DEL_GANADOR = 0;
    private static final Integer UPDATED_PUNTOS_DEL_GANADOR = 1;
    private static final Integer SMALLER_PUNTOS_DEL_GANADOR = 0 - 1;

    private static final String ENTITY_API_URL = "/api/partidas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartidaMockMvc;

    private Partida partida;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partida createEntity(EntityManager em) {
        Partida partida = new Partida().ganador(DEFAULT_GANADOR).perdedor(DEFAULT_PERDEDOR).puntosDelGanador(DEFAULT_PUNTOS_DEL_GANADOR);
        return partida;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partida createUpdatedEntity(EntityManager em) {
        Partida partida = new Partida().ganador(UPDATED_GANADOR).perdedor(UPDATED_PERDEDOR).puntosDelGanador(UPDATED_PUNTOS_DEL_GANADOR);
        return partida;
    }

    @BeforeEach
    public void initTest() {
        partida = createEntity(em);
    }

    @Test
    @Transactional
    void createPartida() throws Exception {
        int databaseSizeBeforeCreate = partidaRepository.findAll().size();
        // Create the Partida
        restPartidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partida)))
            .andExpect(status().isCreated());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeCreate + 1);
        Partida testPartida = partidaList.get(partidaList.size() - 1);
        assertThat(testPartida.getGanador()).isEqualTo(DEFAULT_GANADOR);
        assertThat(testPartida.getPerdedor()).isEqualTo(DEFAULT_PERDEDOR);
        assertThat(testPartida.getPuntosDelGanador()).isEqualTo(DEFAULT_PUNTOS_DEL_GANADOR);
    }

    @Test
    @Transactional
    void createPartidaWithExistingId() throws Exception {
        // Create the Partida with an existing ID
        partida.setId(1L);

        int databaseSizeBeforeCreate = partidaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partida)))
            .andExpect(status().isBadRequest());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGanadorIsRequired() throws Exception {
        int databaseSizeBeforeTest = partidaRepository.findAll().size();
        // set the field null
        partida.setGanador(null);

        // Create the Partida, which fails.

        restPartidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partida)))
            .andExpect(status().isBadRequest());

        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPerdedorIsRequired() throws Exception {
        int databaseSizeBeforeTest = partidaRepository.findAll().size();
        // set the field null
        partida.setPerdedor(null);

        // Create the Partida, which fails.

        restPartidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partida)))
            .andExpect(status().isBadRequest());

        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPuntosDelGanadorIsRequired() throws Exception {
        int databaseSizeBeforeTest = partidaRepository.findAll().size();
        // set the field null
        partida.setPuntosDelGanador(null);

        // Create the Partida, which fails.

        restPartidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partida)))
            .andExpect(status().isBadRequest());

        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPartidas() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList
        restPartidaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partida.getId().intValue())))
            .andExpect(jsonPath("$.[*].ganador").value(hasItem(DEFAULT_GANADOR)))
            .andExpect(jsonPath("$.[*].perdedor").value(hasItem(DEFAULT_PERDEDOR)))
            .andExpect(jsonPath("$.[*].puntosDelGanador").value(hasItem(DEFAULT_PUNTOS_DEL_GANADOR)));
    }

    @Test
    @Transactional
    void getPartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get the partida
        restPartidaMockMvc
            .perform(get(ENTITY_API_URL_ID, partida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partida.getId().intValue()))
            .andExpect(jsonPath("$.ganador").value(DEFAULT_GANADOR))
            .andExpect(jsonPath("$.perdedor").value(DEFAULT_PERDEDOR))
            .andExpect(jsonPath("$.puntosDelGanador").value(DEFAULT_PUNTOS_DEL_GANADOR));
    }

    @Test
    @Transactional
    void getPartidasByIdFiltering() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        Long id = partida.getId();

        defaultPartidaShouldBeFound("id.equals=" + id);
        defaultPartidaShouldNotBeFound("id.notEquals=" + id);

        defaultPartidaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPartidaShouldNotBeFound("id.greaterThan=" + id);

        defaultPartidaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPartidaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPartidasByGanadorIsEqualToSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where ganador equals to DEFAULT_GANADOR
        defaultPartidaShouldBeFound("ganador.equals=" + DEFAULT_GANADOR);

        // Get all the partidaList where ganador equals to UPDATED_GANADOR
        defaultPartidaShouldNotBeFound("ganador.equals=" + UPDATED_GANADOR);
    }

    @Test
    @Transactional
    void getAllPartidasByGanadorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where ganador not equals to DEFAULT_GANADOR
        defaultPartidaShouldNotBeFound("ganador.notEquals=" + DEFAULT_GANADOR);

        // Get all the partidaList where ganador not equals to UPDATED_GANADOR
        defaultPartidaShouldBeFound("ganador.notEquals=" + UPDATED_GANADOR);
    }

    @Test
    @Transactional
    void getAllPartidasByGanadorIsInShouldWork() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where ganador in DEFAULT_GANADOR or UPDATED_GANADOR
        defaultPartidaShouldBeFound("ganador.in=" + DEFAULT_GANADOR + "," + UPDATED_GANADOR);

        // Get all the partidaList where ganador equals to UPDATED_GANADOR
        defaultPartidaShouldNotBeFound("ganador.in=" + UPDATED_GANADOR);
    }

    @Test
    @Transactional
    void getAllPartidasByGanadorIsNullOrNotNull() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where ganador is not null
        defaultPartidaShouldBeFound("ganador.specified=true");

        // Get all the partidaList where ganador is null
        defaultPartidaShouldNotBeFound("ganador.specified=false");
    }

    @Test
    @Transactional
    void getAllPartidasByGanadorContainsSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where ganador contains DEFAULT_GANADOR
        defaultPartidaShouldBeFound("ganador.contains=" + DEFAULT_GANADOR);

        // Get all the partidaList where ganador contains UPDATED_GANADOR
        defaultPartidaShouldNotBeFound("ganador.contains=" + UPDATED_GANADOR);
    }

    @Test
    @Transactional
    void getAllPartidasByGanadorNotContainsSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where ganador does not contain DEFAULT_GANADOR
        defaultPartidaShouldNotBeFound("ganador.doesNotContain=" + DEFAULT_GANADOR);

        // Get all the partidaList where ganador does not contain UPDATED_GANADOR
        defaultPartidaShouldBeFound("ganador.doesNotContain=" + UPDATED_GANADOR);
    }

    @Test
    @Transactional
    void getAllPartidasByPerdedorIsEqualToSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where perdedor equals to DEFAULT_PERDEDOR
        defaultPartidaShouldBeFound("perdedor.equals=" + DEFAULT_PERDEDOR);

        // Get all the partidaList where perdedor equals to UPDATED_PERDEDOR
        defaultPartidaShouldNotBeFound("perdedor.equals=" + UPDATED_PERDEDOR);
    }

    @Test
    @Transactional
    void getAllPartidasByPerdedorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where perdedor not equals to DEFAULT_PERDEDOR
        defaultPartidaShouldNotBeFound("perdedor.notEquals=" + DEFAULT_PERDEDOR);

        // Get all the partidaList where perdedor not equals to UPDATED_PERDEDOR
        defaultPartidaShouldBeFound("perdedor.notEquals=" + UPDATED_PERDEDOR);
    }

    @Test
    @Transactional
    void getAllPartidasByPerdedorIsInShouldWork() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where perdedor in DEFAULT_PERDEDOR or UPDATED_PERDEDOR
        defaultPartidaShouldBeFound("perdedor.in=" + DEFAULT_PERDEDOR + "," + UPDATED_PERDEDOR);

        // Get all the partidaList where perdedor equals to UPDATED_PERDEDOR
        defaultPartidaShouldNotBeFound("perdedor.in=" + UPDATED_PERDEDOR);
    }

    @Test
    @Transactional
    void getAllPartidasByPerdedorIsNullOrNotNull() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where perdedor is not null
        defaultPartidaShouldBeFound("perdedor.specified=true");

        // Get all the partidaList where perdedor is null
        defaultPartidaShouldNotBeFound("perdedor.specified=false");
    }

    @Test
    @Transactional
    void getAllPartidasByPerdedorContainsSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where perdedor contains DEFAULT_PERDEDOR
        defaultPartidaShouldBeFound("perdedor.contains=" + DEFAULT_PERDEDOR);

        // Get all the partidaList where perdedor contains UPDATED_PERDEDOR
        defaultPartidaShouldNotBeFound("perdedor.contains=" + UPDATED_PERDEDOR);
    }

    @Test
    @Transactional
    void getAllPartidasByPerdedorNotContainsSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where perdedor does not contain DEFAULT_PERDEDOR
        defaultPartidaShouldNotBeFound("perdedor.doesNotContain=" + DEFAULT_PERDEDOR);

        // Get all the partidaList where perdedor does not contain UPDATED_PERDEDOR
        defaultPartidaShouldBeFound("perdedor.doesNotContain=" + UPDATED_PERDEDOR);
    }

    @Test
    @Transactional
    void getAllPartidasByPuntosDelGanadorIsEqualToSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where puntosDelGanador equals to DEFAULT_PUNTOS_DEL_GANADOR
        defaultPartidaShouldBeFound("puntosDelGanador.equals=" + DEFAULT_PUNTOS_DEL_GANADOR);

        // Get all the partidaList where puntosDelGanador equals to UPDATED_PUNTOS_DEL_GANADOR
        defaultPartidaShouldNotBeFound("puntosDelGanador.equals=" + UPDATED_PUNTOS_DEL_GANADOR);
    }

    @Test
    @Transactional
    void getAllPartidasByPuntosDelGanadorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where puntosDelGanador not equals to DEFAULT_PUNTOS_DEL_GANADOR
        defaultPartidaShouldNotBeFound("puntosDelGanador.notEquals=" + DEFAULT_PUNTOS_DEL_GANADOR);

        // Get all the partidaList where puntosDelGanador not equals to UPDATED_PUNTOS_DEL_GANADOR
        defaultPartidaShouldBeFound("puntosDelGanador.notEquals=" + UPDATED_PUNTOS_DEL_GANADOR);
    }

    @Test
    @Transactional
    void getAllPartidasByPuntosDelGanadorIsInShouldWork() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where puntosDelGanador in DEFAULT_PUNTOS_DEL_GANADOR or UPDATED_PUNTOS_DEL_GANADOR
        defaultPartidaShouldBeFound("puntosDelGanador.in=" + DEFAULT_PUNTOS_DEL_GANADOR + "," + UPDATED_PUNTOS_DEL_GANADOR);

        // Get all the partidaList where puntosDelGanador equals to UPDATED_PUNTOS_DEL_GANADOR
        defaultPartidaShouldNotBeFound("puntosDelGanador.in=" + UPDATED_PUNTOS_DEL_GANADOR);
    }

    @Test
    @Transactional
    void getAllPartidasByPuntosDelGanadorIsNullOrNotNull() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where puntosDelGanador is not null
        defaultPartidaShouldBeFound("puntosDelGanador.specified=true");

        // Get all the partidaList where puntosDelGanador is null
        defaultPartidaShouldNotBeFound("puntosDelGanador.specified=false");
    }

    @Test
    @Transactional
    void getAllPartidasByPuntosDelGanadorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where puntosDelGanador is greater than or equal to DEFAULT_PUNTOS_DEL_GANADOR
        defaultPartidaShouldBeFound("puntosDelGanador.greaterThanOrEqual=" + DEFAULT_PUNTOS_DEL_GANADOR);

        // Get all the partidaList where puntosDelGanador is greater than or equal to UPDATED_PUNTOS_DEL_GANADOR
        defaultPartidaShouldNotBeFound("puntosDelGanador.greaterThanOrEqual=" + UPDATED_PUNTOS_DEL_GANADOR);
    }

    @Test
    @Transactional
    void getAllPartidasByPuntosDelGanadorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where puntosDelGanador is less than or equal to DEFAULT_PUNTOS_DEL_GANADOR
        defaultPartidaShouldBeFound("puntosDelGanador.lessThanOrEqual=" + DEFAULT_PUNTOS_DEL_GANADOR);

        // Get all the partidaList where puntosDelGanador is less than or equal to SMALLER_PUNTOS_DEL_GANADOR
        defaultPartidaShouldNotBeFound("puntosDelGanador.lessThanOrEqual=" + SMALLER_PUNTOS_DEL_GANADOR);
    }

    @Test
    @Transactional
    void getAllPartidasByPuntosDelGanadorIsLessThanSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where puntosDelGanador is less than DEFAULT_PUNTOS_DEL_GANADOR
        defaultPartidaShouldNotBeFound("puntosDelGanador.lessThan=" + DEFAULT_PUNTOS_DEL_GANADOR);

        // Get all the partidaList where puntosDelGanador is less than UPDATED_PUNTOS_DEL_GANADOR
        defaultPartidaShouldBeFound("puntosDelGanador.lessThan=" + UPDATED_PUNTOS_DEL_GANADOR);
    }

    @Test
    @Transactional
    void getAllPartidasByPuntosDelGanadorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList where puntosDelGanador is greater than DEFAULT_PUNTOS_DEL_GANADOR
        defaultPartidaShouldNotBeFound("puntosDelGanador.greaterThan=" + DEFAULT_PUNTOS_DEL_GANADOR);

        // Get all the partidaList where puntosDelGanador is greater than SMALLER_PUNTOS_DEL_GANADOR
        defaultPartidaShouldBeFound("puntosDelGanador.greaterThan=" + SMALLER_PUNTOS_DEL_GANADOR);
    }

    @Test
    @Transactional
    void getAllPartidasByJuegoIsEqualToSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);
        Juego juego;
        if (TestUtil.findAll(em, Juego.class).isEmpty()) {
            juego = JuegoResourceIT.createEntity(em);
            em.persist(juego);
            em.flush();
        } else {
            juego = TestUtil.findAll(em, Juego.class).get(0);
        }
        em.persist(juego);
        em.flush();
        partida.setJuego(juego);
        partidaRepository.saveAndFlush(partida);
        Long juegoId = juego.getId();

        // Get all the partidaList where juego equals to juegoId
        defaultPartidaShouldBeFound("juegoId.equals=" + juegoId);

        // Get all the partidaList where juego equals to (juegoId + 1)
        defaultPartidaShouldNotBeFound("juegoId.equals=" + (juegoId + 1));
    }

    @Test
    @Transactional
    void getAllPartidasByJugadorIsEqualToSomething() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);
        Jugador jugador;
        if (TestUtil.findAll(em, Jugador.class).isEmpty()) {
            jugador = JugadorResourceIT.createEntity(em);
            em.persist(jugador);
            em.flush();
        } else {
            jugador = TestUtil.findAll(em, Jugador.class).get(0);
        }
        em.persist(jugador);
        em.flush();
        partida.addJugador(jugador);
        partidaRepository.saveAndFlush(partida);
        Long jugadorId = jugador.getId();

        // Get all the partidaList where jugador equals to jugadorId
        defaultPartidaShouldBeFound("jugadorId.equals=" + jugadorId);

        // Get all the partidaList where jugador equals to (jugadorId + 1)
        defaultPartidaShouldNotBeFound("jugadorId.equals=" + (jugadorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartidaShouldBeFound(String filter) throws Exception {
        restPartidaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partida.getId().intValue())))
            .andExpect(jsonPath("$.[*].ganador").value(hasItem(DEFAULT_GANADOR)))
            .andExpect(jsonPath("$.[*].perdedor").value(hasItem(DEFAULT_PERDEDOR)))
            .andExpect(jsonPath("$.[*].puntosDelGanador").value(hasItem(DEFAULT_PUNTOS_DEL_GANADOR)));

        // Check, that the count call also returns 1
        restPartidaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPartidaShouldNotBeFound(String filter) throws Exception {
        restPartidaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPartidaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPartida() throws Exception {
        // Get the partida
        restPartidaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();

        // Update the partida
        Partida updatedPartida = partidaRepository.findById(partida.getId()).get();
        // Disconnect from session so that the updates on updatedPartida are not directly saved in db
        em.detach(updatedPartida);
        updatedPartida.ganador(UPDATED_GANADOR).perdedor(UPDATED_PERDEDOR).puntosDelGanador(UPDATED_PUNTOS_DEL_GANADOR);

        restPartidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartida.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartida))
            )
            .andExpect(status().isOk());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate);
        Partida testPartida = partidaList.get(partidaList.size() - 1);
        assertThat(testPartida.getGanador()).isEqualTo(UPDATED_GANADOR);
        assertThat(testPartida.getPerdedor()).isEqualTo(UPDATED_PERDEDOR);
        assertThat(testPartida.getPuntosDelGanador()).isEqualTo(UPDATED_PUNTOS_DEL_GANADOR);
    }

    @Test
    @Transactional
    void putNonExistingPartida() throws Exception {
        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();
        partida.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partida.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partida))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartida() throws Exception {
        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();
        partida.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partida))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartida() throws Exception {
        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();
        partida.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartidaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partida)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartidaWithPatch() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();

        // Update the partida using partial update
        Partida partialUpdatedPartida = new Partida();
        partialUpdatedPartida.setId(partida.getId());

        partialUpdatedPartida.ganador(UPDATED_GANADOR);

        restPartidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartida.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartida))
            )
            .andExpect(status().isOk());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate);
        Partida testPartida = partidaList.get(partidaList.size() - 1);
        assertThat(testPartida.getGanador()).isEqualTo(UPDATED_GANADOR);
        assertThat(testPartida.getPerdedor()).isEqualTo(DEFAULT_PERDEDOR);
        assertThat(testPartida.getPuntosDelGanador()).isEqualTo(DEFAULT_PUNTOS_DEL_GANADOR);
    }

    @Test
    @Transactional
    void fullUpdatePartidaWithPatch() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();

        // Update the partida using partial update
        Partida partialUpdatedPartida = new Partida();
        partialUpdatedPartida.setId(partida.getId());

        partialUpdatedPartida.ganador(UPDATED_GANADOR).perdedor(UPDATED_PERDEDOR).puntosDelGanador(UPDATED_PUNTOS_DEL_GANADOR);

        restPartidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartida.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartida))
            )
            .andExpect(status().isOk());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate);
        Partida testPartida = partidaList.get(partidaList.size() - 1);
        assertThat(testPartida.getGanador()).isEqualTo(UPDATED_GANADOR);
        assertThat(testPartida.getPerdedor()).isEqualTo(UPDATED_PERDEDOR);
        assertThat(testPartida.getPuntosDelGanador()).isEqualTo(UPDATED_PUNTOS_DEL_GANADOR);
    }

    @Test
    @Transactional
    void patchNonExistingPartida() throws Exception {
        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();
        partida.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partida.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partida))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartida() throws Exception {
        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();
        partida.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partida))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartida() throws Exception {
        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();
        partida.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartidaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partida)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        int databaseSizeBeforeDelete = partidaRepository.findAll().size();

        // Delete the partida
        restPartidaMockMvc
            .perform(delete(ENTITY_API_URL_ID, partida.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
