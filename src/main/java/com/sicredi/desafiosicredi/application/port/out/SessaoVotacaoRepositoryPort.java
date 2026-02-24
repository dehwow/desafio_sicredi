package com.sicredi.desafiosicredi.application.port.out;

import com.sicredi.desafiosicredi.domain.model.SessaoVotacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessaoVotacaoRepositoryPort {
    SessaoVotacao save(SessaoVotacao sessao);
    Optional<SessaoVotacao> findByPautaId(Long pautaId);
    List<SessaoVotacao> findAbertasExpiradas(LocalDateTime agora);
}
