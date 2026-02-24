package com.sicredi.desafiosicredi.adapter.out.persistence.repository;

import com.sicredi.desafiosicredi.adapter.out.persistence.entity.VotoEntity;
import com.sicredi.desafiosicredi.domain.model.OpcaoVoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaVotoRepository extends JpaRepository<VotoEntity, Long> {
    boolean existsByAssociadoIdAndPautaId(UUID associadoId, Long pautaId);
    long countByPautaIdAndOpcaoVoto(Long pautaId, OpcaoVoto opcaoVoto);

    @Query("SELECT v.opcaoVoto, COUNT(v) FROM VotoEntity v WHERE v.pautaId = :pautaId GROUP BY v.opcaoVoto")
    List<Object[]> countVotosByPautaIdGrouped(@Param("pautaId") Long pautaId);
}
