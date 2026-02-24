package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.application.port.in.RegistrarVotoCommand;
import com.sicredi.desafiosicredi.domain.exception.BusinessException;
import com.sicredi.desafiosicredi.domain.model.OpcaoVoto;
import com.sicredi.desafiosicredi.domain.model.SessaoVotacao;
import com.sicredi.desafiosicredi.domain.model.StatusSessao;
import com.sicredi.desafiosicredi.domain.model.Voto;
import com.sicredi.desafiosicredi.application.port.out.SessaoVotacaoRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.VotoRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrarVotoUseCaseTest {

    @Mock
    private SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort;

    @Mock
    private VotoRepositoryPort votoRepositoryPort;

    @InjectMocks
    private RegistrarVotoUseCase registrarVotoUseCase;

    @Test
    @DisplayName("Deve registrar voto com sucesso")
    void deveRegistrarVotoComSucesso() {
        Long pautaId = 1L;
        UUID associadoId = UUID.randomUUID();
        RegistrarVotoCommand command = new RegistrarVotoCommand(pautaId, associadoId, OpcaoVoto.SIM);
        SessaoVotacao sessao = new SessaoVotacao(pautaId, 1);
        Voto voto = new Voto(associadoId, pautaId, OpcaoVoto.SIM);

        when(sessaoVotacaoRepositoryPort.findByPautaId(pautaId)).thenReturn(Optional.of(sessao));
        when(votoRepositoryPort.existsByAssociadoIdAndPautaId(associadoId, pautaId)).thenReturn(false);
        when(votoRepositoryPort.save(any(Voto.class))).thenReturn(voto);

        Voto response = registrarVotoUseCase.execute(command);

        assertNotNull(response);
        assertEquals(OpcaoVoto.SIM, response.getOpcaoVoto());
        verify(votoRepositoryPort, times(1)).save(any(Voto.class));
    }

    @Test
    @DisplayName("Deve falhar se a sessão não existir")
    void deveFalharSeSessaoNaoExistir() {
        Long pautaId = 1L;
        RegistrarVotoCommand command = new RegistrarVotoCommand(pautaId, UUID.randomUUID(), OpcaoVoto.SIM);

        when(sessaoVotacaoRepositoryPort.findByPautaId(pautaId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> registrarVotoUseCase.execute(command));
        assertEquals("Sessão de votação não encontrada para esta pauta.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve falhar se o associado já votou")
    void deveFalharSeAssociadoJaVotou() {
        Long pautaId = 1L;
        UUID associadoId = UUID.randomUUID();
        RegistrarVotoCommand command = new RegistrarVotoCommand(pautaId, associadoId, OpcaoVoto.SIM);
        SessaoVotacao sessao = new SessaoVotacao(pautaId, 1);

        when(sessaoVotacaoRepositoryPort.findByPautaId(pautaId)).thenReturn(Optional.of(sessao));
        when(votoRepositoryPort.existsByAssociadoIdAndPautaId(associadoId, pautaId)).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> registrarVotoUseCase.execute(command));
        assertEquals("Associado já votou nesta pauta.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve falhar se a sessão estiver encerrada")
    void deveFalharSeSessaoEncerrada() {
        Long pautaId = 1L;
        UUID associadoId = UUID.randomUUID();
        RegistrarVotoCommand command = new RegistrarVotoCommand(pautaId, associadoId, OpcaoVoto.SIM);
        
        // Sessão encerrada no passado
        LocalDateTime inicio = LocalDateTime.now().minusMinutes(10);
        LocalDateTime fim = LocalDateTime.now().minusMinutes(5);
        SessaoVotacao sessao = new SessaoVotacao(1L, pautaId, inicio, fim, StatusSessao.ABERTA);

        when(sessaoVotacaoRepositoryPort.findByPautaId(pautaId)).thenReturn(Optional.of(sessao));
        when(votoRepositoryPort.existsByAssociadoIdAndPautaId(associadoId, pautaId)).thenReturn(false);

        BusinessException exception = assertThrows(BusinessException.class, () -> registrarVotoUseCase.execute(command));
        assertEquals("A sessão de votação está encerrada.", exception.getMessage());
    }
}
