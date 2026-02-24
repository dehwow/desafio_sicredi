package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.application.port.in.CriarPautaCommand;
import com.sicredi.desafiosicredi.domain.model.Pauta;
import com.sicredi.desafiosicredi.application.port.out.PautaRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarPautaUseCaseTest {

    @Mock
    private PautaRepositoryPort pautaRepositoryPort;

    @InjectMocks
    private CriarPautaUseCase criarPautaUseCase;

    @Test
    @DisplayName("Deve criar pauta com sucesso")
    void deveCriarPautaComSucesso() {
        CriarPautaCommand command = new CriarPautaCommand("Pauta Teste");
        Pauta pautaSalva = new Pauta(1L, "Pauta Teste");

        when(pautaRepositoryPort.save(any(Pauta.class))).thenReturn(pautaSalva);

        Pauta response = criarPautaUseCase.execute(command);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Pauta Teste", response.getTitulo());
        verify(pautaRepositoryPort, times(1)).save(any(Pauta.class));
    }
}
