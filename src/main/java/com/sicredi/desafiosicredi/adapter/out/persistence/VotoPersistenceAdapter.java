package com.sicredi.desafiosicredi.adapter.out.persistence;

import com.sicredi.desafiosicredi.adapter.out.persistence.entity.VotoEntity;
import com.sicredi.desafiosicredi.adapter.out.persistence.mapper.VotoMapper;
import com.sicredi.desafiosicredi.adapter.out.persistence.repository.JpaVotoRepository;
import com.sicredi.desafiosicredi.domain.model.OpcaoVoto;
import com.sicredi.desafiosicredi.domain.model.Voto;
import com.sicredi.desafiosicredi.application.port.out.VotoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VotoPersistenceAdapter implements VotoRepositoryPort {

    private final JpaVotoRepository repository;
    private final VotoMapper mapper;

    @Override
    public Voto save(Voto voto) {
        VotoEntity entity = mapper.toEntity(voto);
        VotoEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByAssociadoIdAndPautaId(UUID associadoId, Long pautaId) {
        return repository.existsByAssociadoIdAndPautaId(associadoId, pautaId);
    }

    @Override
    public long countByPautaIdAndOpcaoVoto(Long pautaId, OpcaoVoto opcao) {
        return repository.countByPautaIdAndOpcaoVoto(pautaId, opcao);
    }
}
