package com.sicredi.desafiosicredi.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record PautaRequestDTO(
        @NotBlank(message = "O título da pauta é obrigatório.")
        String titulo
) {}
