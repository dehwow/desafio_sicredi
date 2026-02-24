package com.sicredi.desafiosicredi.application.port.in;

import com.sicredi.desafiosicredi.domain.model.Voto;

public interface RegistrarVotoUseCasePort {
    Voto execute(RegistrarVotoCommand command);
}
