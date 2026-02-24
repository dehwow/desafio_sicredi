package com.sicredi.desafiosicredi.application.port.in;

public record CriarPautaCommand(String titulo) {
    public CriarPautaCommand {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título da pauta é obrigatório");
        }
    }
}
