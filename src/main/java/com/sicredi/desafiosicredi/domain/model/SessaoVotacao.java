package com.sicredi.desafiosicredi.domain.model;

import com.sicredi.desafiosicredi.domain.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessaoVotacao {
    private Long id;
    private Long pautaId;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private StatusSessao status;

    public SessaoVotacao(Long pautaId, Integer duracaoEmMinutos) {
        if (pautaId == null) {
            throw new IllegalArgumentException("O ID da pauta é obrigatório");
        }
        this.pautaId = pautaId;
        this.dataHoraInicio = LocalDateTime.now();
        this.dataHoraFim = this.dataHoraInicio.plusMinutes(duracaoEmMinutos != null ? duracaoEmMinutos : 1);
        this.status = StatusSessao.ABERTA;
    }

    public SessaoVotacao(Long id, Long pautaId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, StatusSessao status) {
        this.id = id;
        this.pautaId = pautaId;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.status = status;
    }

    public void validarSePodeVotar() {
        LocalDateTime agora = LocalDateTime.now();
        if (StatusSessao.ENCERRADA.equals(this.status) || !agora.isBefore(dataHoraFim)) {
            this.status = StatusSessao.ENCERRADA;
            throw new BusinessException("A sessão de votação está encerrada.");
        }
        if (agora.isBefore(dataHoraInicio)) {
            throw new BusinessException("A sessão de votação ainda não foi iniciada.");
        }
    }

    public void validarVoto(boolean associadoJaVotou) {
        if (associadoJaVotou) {
            throw new BusinessException("Associado já votou nesta pauta.");
        }
        validarSePodeVotar();
    }

    public boolean estaAberta() {
        LocalDateTime agora = LocalDateTime.now();
        return StatusSessao.ABERTA.equals(this.status) && !agora.isBefore(dataHoraInicio) && agora.isBefore(dataHoraFim);
    }

    public Long getId() {
        return id;
    }

    public Long getPautaId() {
        return pautaId;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public StatusSessao getStatus() {
        // Atualiza status se o tempo expirou
        if (StatusSessao.ABERTA.equals(this.status) && LocalDateTime.now().isAfter(dataHoraFim)) {
            this.status = StatusSessao.ENCERRADA;
        }
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessaoVotacao that = (SessaoVotacao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
