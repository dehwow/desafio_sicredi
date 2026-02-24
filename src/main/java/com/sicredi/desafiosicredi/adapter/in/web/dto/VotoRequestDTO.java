package com.sicredi.desafiosicredi.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

public record VotoRequestDTO(
        @NotNull(message = "O ID da pauta é obrigatório.")
        @Schema(description = "ID da pauta", example = "1")
        Long pautaId,

        @NotNull(message = "O ID do associado é obrigatório.")
        @Schema(description = "ID do associado", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID associadoId,

        @NotBlank(message = "O CPF do associado é obrigatório.")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos.")
        @Schema(description = "CPF do associado", example = "12345678901")
        String cpf,

        @NotBlank(message = "O voto (SIM/NAO) é obrigatório.")
        @Pattern(regexp = "^(?i)(SIM|NAO)$", message = "O voto deve ser SIM ou NAO.")
        @Schema(description = "Voto (SIM/NAO)", example = "SIM")
        String voto
) {}
