package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.application.port.in.ObterResultadoPautaUseCasePort;
import com.sicredi.desafiosicredi.domain.exception.BusinessException;
import com.sicredi.desafiosicredi.domain.model.*;
import com.sicredi.desafiosicredi.application.port.out.*;

import java.time.LocalDateTime;

public class ObterResultadoPautaUseCase implements ObterResultadoPautaUseCasePort {
    private final PautaRepositoryPort pautaRepositoryPort;
    private final VotoRepositoryPort votoRepositoryPort;
    private final SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort;
    private final ResultadoSessaoPublisherPort resultadoSessaoPublisherPort;

    public ObterResultadoPautaUseCase(PautaRepositoryPort pautaRepositoryPort,
                                      VotoRepositoryPort votoRepositoryPort,
                                      SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort,
                                      ResultadoSessaoPublisherPort resultadoSessaoPublisherPort) {
        this.pautaRepositoryPort = pautaRepositoryPort;
        this.votoRepositoryPort = votoRepositoryPort;
        this.sessaoVotacaoRepositoryPort = sessaoVotacaoRepositoryPort;
        this.resultadoSessaoPublisherPort = resultadoSessaoPublisherPort;
    }

    @Override
    public ResultadoPauta execute(Long pautaId) {
        Pauta pauta = pautaRepositoryPort.findById(pautaId)
                .orElseThrow(() -> new BusinessException("Pauta não encontrada."));

        java.util.Map<OpcaoVoto, Long> contagem = votoRepositoryPort.countVotosByPautaIdAgrupado(pautaId);
        long votosSim = contagem.getOrDefault(OpcaoVoto.SIM, 0L);
        long votosNao = contagem.getOrDefault(OpcaoVoto.NAO, 0L);

        sessaoVotacaoRepositoryPort.findByPautaId(pautaId).ifPresent(sessao -> {
            if (sessao.getStatus() == StatusSessao.ENCERRADA && !sessao.isResultadoPublicado()) {
                SessaoEncerradaEvent event = new SessaoEncerradaEvent(
                        pauta.getId(),
                        pauta.getTitulo(),
                        votosSim,
                        votosNao,
                        LocalDateTime.now()
                );
                resultadoSessaoPublisherPort.publish(event);
                sessao.marcarComoPublicado();
                sessaoVotacaoRepositoryPort.save(sessao);
            }
        });

        return new ResultadoPauta(
                pauta.getId(),
                pauta.getTitulo(),
                votosSim,
                votosNao
        );
    }
}
