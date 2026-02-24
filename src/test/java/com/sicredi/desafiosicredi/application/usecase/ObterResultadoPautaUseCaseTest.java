package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.domain.model.OpcaoVoto;
import com.sicredi.desafiosicredi.domain.model.Pauta;
import com.sicredi.desafiosicredi.domain.model.ResultadoPauta;
import com.sicredi.desafiosicredi.application.port.out.PautaRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.VotoRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObterResultadoPautaUseCaseTest {

    @Mock
    private PautaRepositoryPort pautaRepositoryPort;

    @Mock
    private VotoRepositoryPort votoRepositoryPort;

    @InjectMocks
    private ObterResultadoPautaUseCase obterResultadoPautaUseCase;

    @Test
    @DisplayName("Deve obter resultado da pauta com sucesso")
    void deveObterResultadoComSucesso() {
        Long pautaId = 1L;
        Pauta pauta = new Pauta(pautaId, "Pauta Teste");

        when(pautaRepositoryPort.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(votoRepositoryPort.countByPautaIdAndOpcaoVoto(pautaId, OpcaoVoto.SIM)).thenReturn(10L);
        when(votoRepositoryPort.countByPautaIdAndOpcaoVoto(pautaId, OpcaoVoto.NAO)).thenReturn(5L);

        ResultadoPauta response = obterResultadoPautaUseCase.execute(pautaId);

        assertNotNull(response);
        assertEquals(pautaId, response.pautaId());
        assertEquals("Pauta Teste", response.titulo());
        assertEquals(10L, response.votosSim());
        assertEquals(5L, response.votosNao());
    }
}
