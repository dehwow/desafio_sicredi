package com.sicredi.desafiosicredi.adapter.in.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SessaoRequestDTO(
        @NotNull(message = "O ID da pauta é obrigatório.")
        Long pautaId,
        
        @Min(value = 1, message = "A duração mínima da sessão é de 1 minuto.")
        Integer duracaoEmMinutos
) {}
