package com.sicredi.desafiosicredi.adapter.out.persistence;

import com.sicredi.desafiosicredi.adapter.out.persistence.entity.PautaEntity;
import com.sicredi.desafiosicredi.adapter.out.persistence.mapper.PautaMapper;
import com.sicredi.desafiosicredi.adapter.out.persistence.repository.JpaPautaRepository;
import com.sicredi.desafiosicredi.domain.model.Pauta;
import com.sicredi.desafiosicredi.application.port.out.PautaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PautaPersistenceAdapter implements PautaRepositoryPort {

    private final JpaPautaRepository repository;
    private final PautaMapper mapper;

    @Override
    public Pauta save(Pauta pauta) {
        PautaEntity entity = mapper.toEntity(pauta);
        PautaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Pauta> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
