package com.sicredi.desafiosicredi.application.usecase;

import com.sicredi.desafiosicredi.application.port.in.ObterResultadoPautaUseCasePort;
import com.sicredi.desafiosicredi.domain.exception.BusinessException;
import com.sicredi.desafiosicredi.domain.model.OpcaoVoto;
import com.sicredi.desafiosicredi.domain.model.Pauta;
import com.sicredi.desafiosicredi.domain.model.ResultadoPauta;
import com.sicredi.desafiosicredi.application.port.out.PautaRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.VotoRepositoryPort;

public class ObterResultadoPautaUseCase implements ObterResultadoPautaUseCasePort {
    private final PautaRepositoryPort pautaRepositoryPort;
    private final VotoRepositoryPort votoRepositoryPort;

    public ObterResultadoPautaUseCase(PautaRepositoryPort pautaRepositoryPort, VotoRepositoryPort votoRepositoryPort) {
        this.pautaRepositoryPort = pautaRepositoryPort;
        this.votoRepositoryPort = votoRepositoryPort;
    }

    @Override
    public ResultadoPauta execute(Long pautaId) {
        Pauta pauta = pautaRepositoryPort.findById(pautaId)
                .orElseThrow(() -> new BusinessException("Pauta não encontrada."));

        long votosSim = votoRepositoryPort.countByPautaIdAndOpcaoVoto(pautaId, OpcaoVoto.SIM);
        long votosNao = votoRepositoryPort.countByPautaIdAndOpcaoVoto(pautaId, OpcaoVoto.NAO);

        return new ResultadoPauta(
                pauta.getId(),
                pauta.getTitulo(),
                votosSim,
                votosNao
        );
    }
}
