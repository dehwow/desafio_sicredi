package com.sicredi.desafiosicredi.application.port.in;

import com.sicredi.desafiosicredi.domain.model.SessaoVotacao;

public interface AbrirSessaoUseCasePort {
    SessaoVotacao execute(AbrirSessaoCommand command);
}
