package com.sicredi.desafiosicredi.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PautaTest {

    @Test
    @DisplayName("Deve abrir uma sessão para a pauta")
    void deveAbrirSessao() {
        Pauta pauta = new Pauta(1L, "Teste");
        SessaoVotacao sessao = pauta.abrirSessao(10);
        
        assertNotNull(sessao);
        assertEquals(pauta.getId(), sessao.getPautaId());
        assertEquals(10, java.time.Duration.between(sessao.getDataHoraInicio(), sessao.getDataHoraFim()).toMinutes());
    }

    @Test
    @DisplayName("Deve falhar ao abrir sessão para pauta sem ID")
    void deveFalharAoAbrirSessaoSemId() {
        Pauta pauta = new Pauta("Teste");
        
        assertThrows(IllegalStateException.class, () -> pauta.abrirSessao(10));
    }

    @Test
    @DisplayName("Deve falhar ao criar pauta sem título")
    void deveFalharAoCriarPautaSemTitulo() {
        assertThrows(IllegalArgumentException.class, () -> new Pauta(null));
        assertThrows(IllegalArgumentException.class, () -> new Pauta(" "));
    }
}
