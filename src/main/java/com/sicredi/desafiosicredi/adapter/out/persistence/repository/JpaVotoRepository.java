package com.sicredi.desafiosicredi.adapter.out.persistence.repository;

import com.sicredi.desafiosicredi.adapter.out.persistence.entity.VotoEntity;
import com.sicredi.desafiosicredi.domain.model.OpcaoVoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaVotoRepository extends JpaRepository<VotoEntity, Long> {
    boolean existsByAssociadoIdAndPautaId(UUID associadoId, Long pautaId);
    long countByPautaIdAndOpcaoVoto(Long pautaId, OpcaoVoto opcaoVoto);
}
