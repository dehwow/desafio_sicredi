package com.sicredi.desafiosicredi.application.port.out;

import com.sicredi.desafiosicredi.domain.model.SessaoEncerradaEvent;

public interface ResultadoSessaoPublisherPort {
    void publish(SessaoEncerradaEvent event);
}
