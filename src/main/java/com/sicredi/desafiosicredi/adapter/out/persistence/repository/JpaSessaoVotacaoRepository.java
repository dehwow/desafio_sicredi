package com.sicredi.desafiosicredi.adapter.out.persistence.repository;

import com.sicredi.desafiosicredi.adapter.out.persistence.entity.SessaoVotacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaSessaoVotacaoRepository extends JpaRepository<SessaoVotacaoEntity, Long> {
    Optional<SessaoVotacaoEntity> findByPautaId(Long pautaId);
}
