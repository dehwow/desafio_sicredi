package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.application.port.in.AbrirSessaoCommand;
import com.sicredi.desafiosicredi.domain.exception.BusinessException;
import com.sicredi.desafiosicredi.domain.model.Pauta;
import com.sicredi.desafiosicredi.domain.model.SessaoVotacao;
import com.sicredi.desafiosicredi.application.port.out.PautaRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.SessaoVotacaoRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbrirSessaoUseCaseTest {

    @Mock
    private PautaRepositoryPort pautaRepositoryPort;

    @Mock
    private SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort;

    @InjectMocks
    private AbrirSessaoUseCase abrirSessaoUseCase;

    @Test
    @DisplayName("Deve abrir sessão com sucesso")
    void deveAbrirSessaoComSucesso() {
        Long pautaId = 1L;
        Pauta pauta = new Pauta(pautaId, "Pauta Teste");
        AbrirSessaoCommand command = new AbrirSessaoCommand(pautaId, 5);
        SessaoVotacao sessaoSalva = new SessaoVotacao(100L, pautaId, java.time.LocalDateTime.now(), java.time.LocalDateTime.now().plusMinutes(5), com.sicredi.desafiosicredi.domain.model.StatusSessao.ABERTA);

        when(pautaRepositoryPort.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(sessaoVotacaoRepositoryPort.save(any(SessaoVotacao.class))).thenReturn(sessaoSalva);

        SessaoVotacao response = abrirSessaoUseCase.execute(command);

        assertNotNull(response);
        assertEquals(pautaId, response.getPautaId());
        assertEquals(com.sicredi.desafiosicredi.domain.model.StatusSessao.ABERTA, response.getStatus());
        verify(sessaoVotacaoRepositoryPort, times(1)).save(any(SessaoVotacao.class));
    }

    @Test
    @DisplayName("Deve falhar ao abrir sessão para pauta inexistente")
    void deveFalharPautaInexistente() {
        Long pautaId = 1L;
        AbrirSessaoCommand command = new AbrirSessaoCommand(pautaId, 1);

        when(pautaRepositoryPort.findById(pautaId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> abrirSessaoUseCase.execute(command));
        assertEquals("Pauta não encontrada.", exception.getMessage());
    }
}
