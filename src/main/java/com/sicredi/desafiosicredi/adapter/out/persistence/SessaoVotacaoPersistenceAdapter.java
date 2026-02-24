package com.sicredi.desafiosicredi.adapter.out.persistence;

import com.sicredi.desafiosicredi.adapter.out.persistence.entity.SessaoVotacaoEntity;
import com.sicredi.desafiosicredi.adapter.out.persistence.mapper.SessaoVotacaoMapper;
import com.sicredi.desafiosicredi.adapter.out.persistence.repository.JpaSessaoVotacaoRepository;
import com.sicredi.desafiosicredi.domain.model.SessaoVotacao;
import com.sicredi.desafiosicredi.application.port.out.SessaoVotacaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SessaoVotacaoPersistenceAdapter implements SessaoVotacaoRepositoryPort {

    private final JpaSessaoVotacaoRepository repository;
    private final SessaoVotacaoMapper mapper;

    @Override
    public SessaoVotacao save(SessaoVotacao sessao) {
        SessaoVotacaoEntity entity = mapper.toEntity(sessao);
        SessaoVotacaoEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<SessaoVotacao> findByPautaId(Long pautaId) {
        return repository.findByPautaId(pautaId).map(mapper::toDomain);
    }
}
