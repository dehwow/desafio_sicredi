package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.application.port.in.CriarPautaCommand;
import com.sicredi.desafiosicredi.application.port.in.CriarPautaUseCasePort;
import com.sicredi.desafiosicredi.domain.model.Pauta;
import com.sicredi.desafiosicredi.application.port.out.PautaRepositoryPort;

public class CriarPautaUseCase implements CriarPautaUseCasePort {
    private final PautaRepositoryPort pautaRepositoryPort;

    public CriarPautaUseCase(PautaRepositoryPort pautaRepositoryPort) {
        this.pautaRepositoryPort = pautaRepositoryPort;
    }

    @Override
    public Pauta execute(CriarPautaCommand command) {
        Pauta pauta = new Pauta(command.titulo());
        return pautaRepositoryPort.save(pauta);
    }
}
