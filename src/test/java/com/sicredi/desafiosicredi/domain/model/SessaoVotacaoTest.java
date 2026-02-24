package com.sicredi.desafiosicredi.domain.model;

import com.sicredi.desafiosicredi.domain.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SessaoVotacaoTest {

    @Test
    @DisplayName("Deve criar sessão com duração default de 1 minuto")
    void deveCriarSessaoComDuracaoDefault() {
        SessaoVotacao sessao = new SessaoVotacao(1L, null);
        
        assertEquals(StatusSessao.ABERTA, sessao.getStatus());
        assertTrue(sessao.estaAberta());
        assertTrue(sessao.getDataHoraFim().isAfter(sessao.getDataHoraInicio()));
        assertEquals(sessao.getDataHoraInicio().plusMinutes(1), sessao.getDataHoraFim());
    }

    @Test
    @DisplayName("Deve criar sessão com duração customizada")
    void deveCriarSessaoComDuracaoCustomizada() {
        SessaoVotacao sessao = new SessaoVotacao(1L, 5);
        
        assertEquals(sessao.getDataHoraInicio().plusMinutes(5), sessao.getDataHoraFim());
    }

    @Test
    @DisplayName("Deve falhar ao validar voto em sessão encerrada por tempo")
    void deveFalharAoValidarVotoEmSessaoEncerradaPorTempo() {
        // Criar uma sessão que já expirou
        LocalDateTime inicio = LocalDateTime.now().minusMinutes(10);
        LocalDateTime fim = LocalDateTime.now().minusMinutes(5);
        SessaoVotacao sessao = new SessaoVotacao(1L, 1L, inicio, fim, StatusSessao.ABERTA, false);

        assertThrows(BusinessException.class, sessao::validarSePodeVotar);
        assertEquals(StatusSessao.ENCERRADA, sessao.getStatus());
    }

    @Test
    @DisplayName("Deve falhar ao validar voto em sessão explicitamente encerrada")
    void deveFalharAoValidarVotoEmSessaoEncerrada() {
        SessaoVotacao sessao = new SessaoVotacao(1L, 1);
        // Forçar encerramento (em um cenário real isso viria do banco ou alteração de estado)
        SessaoVotacao sessaoEncerrada = new SessaoVotacao(1L, 1L, sessao.getDataHoraInicio(), sessao.getDataHoraFim(), StatusSessao.ENCERRADA, false);

        assertThrows(BusinessException.class, sessaoEncerrada::validarSePodeVotar);
    }

    @Test
    @DisplayName("Deve validar voto com sucesso em sessão aberta")
    void deveValidarVotoComSucesso() {
        SessaoVotacao sessao = new SessaoVotacao(1L, 1);
        
        assertDoesNotThrow(sessao::validarVoto);
    }
}
