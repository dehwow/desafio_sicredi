package com.sicredi.desafiosicredi.application.port.in;

public record AbrirSessaoCommand(Long pautaId, Integer duracaoEmMinutos) {
    public AbrirSessaoCommand {
        if (pautaId == null) {
            throw new IllegalArgumentException("O ID da pauta é obrigatório");
        }
    }
}
