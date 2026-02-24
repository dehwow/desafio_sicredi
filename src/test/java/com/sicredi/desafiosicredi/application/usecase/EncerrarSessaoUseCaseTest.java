package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.application.port.out.PautaRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.ResultadoSessaoPublisherPort;
import com.sicredi.desafiosicredi.application.port.out.SessaoVotacaoRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.VotoRepositoryPort;
import com.sicredi.desafiosicredi.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EncerrarSessaoUseCaseTest {

    @Mock
    private SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort;

    @Mock
    private PautaRepositoryPort pautaRepositoryPort;

    @Mock
    private VotoRepositoryPort votoRepositoryPort;

    @Mock
    private ResultadoSessaoPublisherPort resultadoSessaoPublisherPort;

    @InjectMocks
    private EncerrarSessaoUseCase encerrarSessaoUseCase;

    @Test
    @DisplayName("Deve encerrar sessões expiradas e publicar resultados")
    void deveEncerrarSessoesExpiradas() {
        Long pautaId = 1L;
        Pauta pauta = new Pauta(pautaId, "Pauta Teste");
        LocalDateTime inicio = LocalDateTime.now().minusMinutes(10);
        LocalDateTime fim = LocalDateTime.now().minusMinutes(5);
        SessaoVotacao sessao = new SessaoVotacao(100L, pautaId, inicio, fim, StatusSessao.ABERTA, false);

        when(sessaoVotacaoRepositoryPort.findAbertasExpiradas(any(LocalDateTime.class)))
                .thenReturn(List.of(sessao));
        when(pautaRepositoryPort.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(votoRepositoryPort.countVotosByPautaIdAgrupado(pautaId)).thenReturn(Map.of(OpcaoVoto.SIM, 5L, OpcaoVoto.NAO, 3L));

        encerrarSessaoUseCase.executar();

        verify(resultadoSessaoPublisherPort, times(1)).publish(any(SessaoEncerradaEvent.class));
        verify(sessaoVotacaoRepositoryPort, times(1)).save(argThat(SessaoVotacao::isResultadoPublicado));
    }

    @Test
    @DisplayName("Não deve processar nada se não houver sessões expiradas")
    void naoDeveProcessarSeNaoHouverSessoes() {
        when(sessaoVotacaoRepositoryPort.findAbertasExpiradas(any(LocalDateTime.class)))
                .thenReturn(List.of());

        encerrarSessaoUseCase.executar();

        verifyNoInteractions(pautaRepositoryPort);
        verifyNoInteractions(votoRepositoryPort);
        verifyNoInteractions(resultadoSessaoPublisherPort);
    }
}
