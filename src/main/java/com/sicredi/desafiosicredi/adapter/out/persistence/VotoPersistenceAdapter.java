package com.sicredi.desafiosicredi.adapter.out.persistence;

import com.sicredi.desafiosicredi.adapter.out.persistence.entity.VotoEntity;
import com.sicredi.desafiosicredi.adapter.out.persistence.mapper.VotoMapper;
import com.sicredi.desafiosicredi.adapter.out.persistence.repository.JpaVotoRepository;
import com.sicredi.desafiosicredi.domain.exception.BusinessException;
import com.sicredi.desafiosicredi.domain.model.OpcaoVoto;
import com.sicredi.desafiosicredi.domain.model.Voto;
import com.sicredi.desafiosicredi.application.port.out.VotoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VotoPersistenceAdapter implements VotoRepositoryPort {

    private final JpaVotoRepository repository;
    private final VotoMapper mapper;

    @Override
    public Voto save(Voto voto) {
        try {
            VotoEntity entity = mapper.toEntity(voto);
            VotoEntity savedEntity = repository.saveAndFlush(entity);
            return mapper.toDomain(savedEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException("Associado já votou nesta pauta.");
        }
    }

    @Override
    public boolean existsByAssociadoIdAndPautaId(UUID associadoId, Long pautaId) {
        return repository.existsByAssociadoIdAndPautaId(associadoId, pautaId);
    }

    @Override
    public long countByPautaIdAndOpcaoVoto(Long pautaId, OpcaoVoto opcao) {
        return repository.countByPautaIdAndOpcaoVoto(pautaId, opcao);
    }

    @Override
    public Map<OpcaoVoto, Long> countVotosByPautaIdAgrupado(Long pautaId) {
        List<Object[]> results = repository.countVotosByPautaIdGrouped(pautaId);
        Map<OpcaoVoto, Long> contagem = new EnumMap<>(OpcaoVoto.class);
        for (Object[] row : results) {
            OpcaoVoto opcao = (OpcaoVoto) row[0];
            Long count = (Long) row[1];
            contagem.put(opcao, count);
        }
        return contagem;
    }
}
