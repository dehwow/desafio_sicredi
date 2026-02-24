package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.domain.model.OpcaoVoto;
import com.sicredi.desafiosicredi.domain.model.Pauta;
import com.sicredi.desafiosicredi.domain.model.ResultadoPauta;
import com.sicredi.desafiosicredi.application.port.out.PautaRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.ResultadoSessaoPublisherPort;
import com.sicredi.desafiosicredi.application.port.out.SessaoVotacaoRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.VotoRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObterResultadoPautaUseCaseTest {

    @Mock
    private PautaRepositoryPort pautaRepositoryPort;

    @Mock
    private VotoRepositoryPort votoRepositoryPort;

    @Mock
    private SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort;

    @Mock
    private ResultadoSessaoPublisherPort resultadoSessaoPublisherPort;

    @InjectMocks
    private ObterResultadoPautaUseCase obterResultadoPautaUseCase;

    @Test
    @DisplayName("Deve obter resultado da pauta com sucesso")
    void deveObterResultadoComSucesso() {
        Long pautaId = 1L;
        Pauta pauta = new Pauta(pautaId, "Pauta Teste");

        when(pautaRepositoryPort.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(votoRepositoryPort.countVotosByPautaIdAgrupado(pautaId)).thenReturn(Map.of(OpcaoVoto.SIM, 10L, OpcaoVoto.NAO, 5L));
        when(sessaoVotacaoRepositoryPort.findByPautaId(pautaId)).thenReturn(Optional.empty());

        ResultadoPauta response = obterResultadoPautaUseCase.execute(pautaId);

        assertNotNull(response);
        assertEquals(pautaId, response.pautaId());
        assertEquals("Pauta Teste", response.titulo());
        assertEquals(10L, response.votosSim());
        assertEquals(5L, response.votosNao());
    }
}
