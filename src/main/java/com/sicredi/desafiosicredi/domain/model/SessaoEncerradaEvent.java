package com.sicredi.desafiosicredi.domain.model;

import java.time.LocalDateTime;

public record SessaoEncerradaEvent(
        Long pautaId,
        String pautaTitulo,
        Long votosSim,
        Long votosNao,
        LocalDateTime dataHoraEncerrada
) {
}
