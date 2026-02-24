package com.sicredi.desafiosicredi.application.port.out;

import com.sicredi.desafiosicredi.domain.model.OpcaoVoto;
import com.sicredi.desafiosicredi.domain.model.Voto;

import java.util.Map;
import java.util.UUID;

public interface VotoRepositoryPort {
    Voto save(Voto voto);
    boolean existsByAssociadoIdAndPautaId(UUID associadoId, Long pautaId);
    long countByPautaIdAndOpcaoVoto(Long pautaId, OpcaoVoto opcao);
    Map<OpcaoVoto, Long> countVotosByPautaIdAgrupado(Long pautaId);
}
