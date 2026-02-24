package com.sicredi.desafiosicredi.application.port.in;

import com.sicredi.desafiosicredi.domain.model.ResultadoPauta;

public interface ObterResultadoPautaUseCasePort {
    ResultadoPauta execute(Long pautaId);
}
