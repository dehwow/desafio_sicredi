package com.sicredi.desafiosicredi.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

public record VotoRequestDTO(
        @NotNull(message = "O ID da pauta é obrigatório.")
        Long pautaId,

        @NotNull(message = "O ID do associado é obrigatório.")
        UUID associadoId,

        @NotBlank(message = "O voto (SIM/NAO) é obrigatório.")
        @Pattern(regexp = "^(?i)(SIM|NAO)$", message = "O voto deve ser SIM ou NAO.")
        String voto
) {}
