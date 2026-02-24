package com.sicredi.desafiosicredi.adapter.out.persistence.repository;

import com.sicredi.desafiosicredi.adapter.out.persistence.entity.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPautaRepository extends JpaRepository<PautaEntity, Long> {
}
