package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.application.port.in.AbrirSessaoCommand;
import com.sicredi.desafiosicredi.application.port.in.AbrirSessaoUseCasePort;
import com.sicredi.desafiosicredi.domain.exception.BusinessException;
import com.sicredi.desafiosicredi.domain.model.Pauta;
import com.sicredi.desafiosicredi.domain.model.SessaoVotacao;
import com.sicredi.desafiosicredi.application.port.out.PautaRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.SessaoVotacaoRepositoryPort;

public class AbrirSessaoUseCase implements AbrirSessaoUseCasePort {
    private final PautaRepositoryPort pautaRepositoryPort;
    private final SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort;

    public AbrirSessaoUseCase(PautaRepositoryPort pautaRepositoryPort, SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort) {
        this.pautaRepositoryPort = pautaRepositoryPort;
        this.sessaoVotacaoRepositoryPort = sessaoVotacaoRepositoryPort;
    }

    @Override
    public SessaoVotacao execute(AbrirSessaoCommand command) {
        Pauta pauta = pautaRepositoryPort.findById(command.pautaId())
                .orElseThrow(() -> new BusinessException("Pauta não encontrada."));

        SessaoVotacao sessao = pauta.abrirSessao(command.duracaoEmMinutos());
        return sessaoVotacaoRepositoryPort.save(sessao);
    }
}
