package com.sicredi.desafiosicredi.application.port.out;

import com.sicredi.desafiosicredi.domain.model.Pauta;
import java.util.Optional;

public interface PautaRepositoryPort {
    Pauta save(Pauta pauta);
    Optional<Pauta> findById(Long id);
}
