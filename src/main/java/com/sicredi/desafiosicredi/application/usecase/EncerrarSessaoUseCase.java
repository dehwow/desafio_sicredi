package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.application.port.out.PautaRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.ResultadoSessaoPublisherPort;
import com.sicredi.desafiosicredi.application.port.out.SessaoVotacaoRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.VotoRepositoryPort;
import com.sicredi.desafiosicredi.domain.model.OpcaoVoto;
import com.sicredi.desafiosicredi.domain.model.Pauta;
import com.sicredi.desafiosicredi.domain.model.SessaoEncerradaEvent;
import com.sicredi.desafiosicredi.domain.model.SessaoVotacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class EncerrarSessaoUseCase {

    private final SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort;
    private final PautaRepositoryPort pautaRepositoryPort;
    private final VotoRepositoryPort votoRepositoryPort;
    private final ResultadoSessaoPublisherPort resultadoSessaoPublisherPort;

    public void executar() {
        LocalDateTime agora = LocalDateTime.now();
        List<SessaoVotacao> sessoesParaEncerrar = sessaoVotacaoRepositoryPort.findAbertasExpiradas(agora);

        if (!sessoesParaEncerrar.isEmpty()) {
            log.info("Encontradas {} sessoes para encerrar.", sessoesParaEncerrar.size());
        }

        for (SessaoVotacao sessao : sessoesParaEncerrar) {
            encerrarSessao(sessao);
        }
    }

    private void encerrarSessao(SessaoVotacao sessao) {
        try {
            Pauta pauta = pautaRepositoryPort.findById(sessao.getPautaId())
                    .orElseThrow(() -> new RuntimeException("Pauta nao encontrada para o ID: " + sessao.getPautaId()));

            java.util.Map<OpcaoVoto, Long> contagem = votoRepositoryPort.countVotosByPautaIdAgrupado(pauta.getId());
            long votosSim = contagem.getOrDefault(OpcaoVoto.SIM, 0L);
            long votosNao = contagem.getOrDefault(OpcaoVoto.NAO, 0L);

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

            log.info("Sessao da pauta {} encerrada e resultado publicado com sucesso.", pauta.getId());
        } catch (Exception e) {
            log.error("Erro ao processar encerramento da sessao da pauta {}: {}", sessao.getPautaId(), e.getMessage());
        }
    }
}
