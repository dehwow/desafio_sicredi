package com.sicredi.desafiosicredi.adapter.in.web.dto;

import java.time.LocalDateTime;

public record SessaoResponseDTO(Long id, Long pautaId, LocalDateTime dataHoraFim, String status) {}
