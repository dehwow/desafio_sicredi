package com.sicredi.desafiosicredi.adapter.out.persistence.entity;

import com.sicredi.desafiosicredi.domain.model.OpcaoVoto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "voto",
        uniqueConstraints = @UniqueConstraint(name = "uk_voto_associado_pauta", columnNames = {"associado_id", "pauta_id"}),
        indexes = @Index(name = "idx_voto_pauta_id", columnList = "pauta_id"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "associado_id")
    private UUID associadoId;

    @Column(name = "pauta_id")
    private Long pautaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "opcao_voto")
    private OpcaoVoto opcaoVoto;
}
