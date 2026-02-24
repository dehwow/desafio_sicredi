package com.sicredi.desafiosicredi.application.port.in;

import com.sicredi.desafiosicredi.domain.model.OpcaoVoto;
import java.util.UUID;

public record RegistrarVotoCommand(Long pautaId, UUID associadoId, OpcaoVoto voto) {
    public RegistrarVotoCommand {
        if (pautaId == null) {
            throw new IllegalArgumentException("O ID da pauta é obrigatório");
        }
        if (associadoId == null) {
            throw new IllegalArgumentException("O ID do associado é obrigatório");
        }
        if (voto == null) {
            throw new IllegalArgumentException("O voto é obrigatório");
        }
    }
}
