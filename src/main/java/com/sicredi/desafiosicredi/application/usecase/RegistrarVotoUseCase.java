package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.application.port.in.RegistrarVotoCommand;
import com.sicredi.desafiosicredi.application.port.in.RegistrarVotoUseCasePort;
import com.sicredi.desafiosicredi.domain.exception.BusinessException;
import com.sicredi.desafiosicredi.domain.model.SessaoVotacao;
import com.sicredi.desafiosicredi.domain.model.Voto;
import com.sicredi.desafiosicredi.application.port.out.CpfValidationPort;
import com.sicredi.desafiosicredi.application.port.out.SessaoVotacaoRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.VotoRepositoryPort;

public class RegistrarVotoUseCase implements RegistrarVotoUseCasePort {
    private final SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort;
    private final VotoRepositoryPort votoRepositoryPort;
    private final CpfValidationPort cpfValidationPort;

    public RegistrarVotoUseCase(SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort,
                                VotoRepositoryPort votoRepositoryPort,
                                CpfValidationPort cpfValidationPort) {
        this.sessaoVotacaoRepositoryPort = sessaoVotacaoRepositoryPort;
        this.votoRepositoryPort = votoRepositoryPort;
        this.cpfValidationPort = cpfValidationPort;
    }

    @Override
    public Voto execute(RegistrarVotoCommand command) {
        if (!cpfValidationPort.isAbleToVote(command.cpf())) {
            throw new BusinessException("Associado não está apto a votar.");
        }

        SessaoVotacao sessao = sessaoVotacaoRepositoryPort.findByPautaId(command.pautaId())
                .orElseThrow(() -> new BusinessException("Sessão de votação não encontrada para esta pauta."));

        sessao.validarVoto();

        Voto voto = new Voto(command.associadoId(), command.pautaId(), command.voto());
        return votoRepositoryPort.save(voto);
    }
}
