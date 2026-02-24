package com.sicredi.desafiosicredi.infrastructure.scheduler;

import com.sicredi.desafiosicredi.application.usecase.EncerrarSessaoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessaoScheduler {

    private final EncerrarSessaoUseCase encerrarSessaoUseCase;

    @Scheduled(fixedRateString = "${app.scheduler.encerrar-sessao-rate:60000}")
    public void encerrarSessoesExpiradas() {
        encerrarSessaoUseCase.executar();
    }
}
