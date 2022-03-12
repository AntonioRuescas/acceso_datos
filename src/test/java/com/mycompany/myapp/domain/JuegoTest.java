package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JuegoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Juego.class);
        Juego juego1 = new Juego();
        juego1.setId(1L);
        Juego juego2 = new Juego();
        juego2.setId(juego1.getId());
        assertThat(juego1).isEqualTo(juego2);
        juego2.setId(2L);
        assertThat(juego1).isNotEqualTo(juego2);
        juego1.setId(null);
        assertThat(juego1).isNotEqualTo(juego2);
    }
}
