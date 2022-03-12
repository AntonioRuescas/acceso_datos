package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartidaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partida.class);
        Partida partida1 = new Partida();
        partida1.setId(1L);
        Partida partida2 = new Partida();
        partida2.setId(partida1.getId());
        assertThat(partida1).isEqualTo(partida2);
        partida2.setId(2L);
        assertThat(partida1).isNotEqualTo(partida2);
        partida1.setId(null);
        assertThat(partida1).isNotEqualTo(partida2);
    }
}
