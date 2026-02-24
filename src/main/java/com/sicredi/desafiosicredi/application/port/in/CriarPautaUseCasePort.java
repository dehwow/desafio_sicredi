package com.sicredi.desafiosicredi.application.port.in;

import com.sicredi.desafiosicredi.domain.model.Pauta;

public interface CriarPautaUseCasePort {
    Pauta execute(CriarPautaCommand command);
}
