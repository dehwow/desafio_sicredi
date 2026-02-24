package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.application.port.in.RegistrarVotoCommand;
import com.sicredi.desafiosicredi.application.port.in.RegistrarVotoUseCasePort;
import com.sicredi.desafiosicredi.domain.exception.BusinessException;
import com.sicredi.desafiosicredi.domain.model.SessaoVotacao;
import com.sicredi.desafiosicredi.domain.model.Voto;
import com.sicredi.desafiosicredi.application.port.out.SessaoVotacaoRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.VotoRepositoryPort;

public class RegistrarVotoUseCase implements RegistrarVotoUseCasePort {
    private final SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort;
    private final VotoRepositoryPort votoRepositoryPort;

    public RegistrarVotoUseCase(SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort, VotoRepositoryPort votoRepositoryPort) {
        this.sessaoVotacaoRepositoryPort = sessaoVotacaoRepositoryPort;
        this.votoRepositoryPort = votoRepositoryPort;
    }

    @Override
    public Voto execute(RegistrarVotoCommand command) {
        SessaoVotacao sessao = sessaoVotacaoRepositoryPort.findByPautaId(command.pautaId())
                .orElseThrow(() -> new BusinessException("Sessão de votação não encontrada para esta pauta."));

        boolean jaVotou = votoRepositoryPort.existsByAssociadoIdAndPautaId(command.associadoId(), command.pautaId());
        
        // Regras de negócio: valida se sessão está aberta e se associado já votou
        sessao.validarVoto(jaVotou);

        Voto voto = new Voto(command.associadoId(), command.pautaId(), command.voto());
        return votoRepositoryPort.save(voto);
    }
}
