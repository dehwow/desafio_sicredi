package com.sicredi.desafiosicredi.domain.model;

import java.util.Objects;
import java.util.UUID;

public class Voto {
    private UUID associadoId;
    private Long pautaId;
    private OpcaoVoto opcaoVoto;

    public Voto(UUID associadoId, Long pautaId, OpcaoVoto opcaoVoto) {
        if (associadoId == null) {
            throw new IllegalArgumentException("O ID do associado é obrigatório");
        }
        if (pautaId == null) {
            throw new IllegalArgumentException("O ID da pauta é obrigatório");
        }
        if (opcaoVoto == null) {
            throw new IllegalArgumentException("A opção de voto é obrigatória");
        }
        this.associadoId = associadoId;
        this.pautaId = pautaId;
        this.opcaoVoto = opcaoVoto;
    }

    public UUID getAssociadoId() {
        return associadoId;
    }

    public Long getPautaId() {
        return pautaId;
    }

    public OpcaoVoto getOpcaoVoto() {
        return opcaoVoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voto voto = (Voto) o;
        return Objects.equals(associadoId, voto.associadoId) && Objects.equals(pautaId, voto.pautaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(associadoId, pautaId);
    }
}
