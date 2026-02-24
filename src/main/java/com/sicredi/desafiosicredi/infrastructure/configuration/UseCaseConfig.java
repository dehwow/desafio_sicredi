package com.sicredi.desafiosicredi.infrastructure.configuration;

import com.sicredi.desafiosicredi.application.port.in.AbrirSessaoUseCasePort;
import com.sicredi.desafiosicredi.application.port.in.CriarPautaUseCasePort;
import com.sicredi.desafiosicredi.application.port.in.ObterResultadoPautaUseCasePort;
import com.sicredi.desafiosicredi.application.port.in.RegistrarVotoUseCasePort;
import com.sicredi.desafiosicredi.application.usecase.AbrirSessaoUseCase;
import com.sicredi.desafiosicredi.application.usecase.CriarPautaUseCase;
import com.sicredi.desafiosicredi.application.usecase.ObterResultadoPautaUseCase;
import com.sicredi.desafiosicredi.application.usecase.RegistrarVotoUseCase;
import com.sicredi.desafiosicredi.application.port.out.PautaRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.SessaoVotacaoRepositoryPort;
import com.sicredi.desafiosicredi.application.port.out.VotoRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CriarPautaUseCasePort criarPautaUseCase(PautaRepositoryPort pautaRepositoryPort) {
        return new CriarPautaUseCase(pautaRepositoryPort);
    }

    @Bean
    public AbrirSessaoUseCasePort abrirSessaoUseCase(PautaRepositoryPort pautaRepositoryPort,
                                                     SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort) {
        return new AbrirSessaoUseCase(pautaRepositoryPort, sessaoVotacaoRepositoryPort);
    }

    @Bean
    public RegistrarVotoUseCasePort registrarVotoUseCase(SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort,
                                                         VotoRepositoryPort votoRepositoryPort) {
        return new RegistrarVotoUseCase(sessaoVotacaoRepositoryPort, votoRepositoryPort);
    }

    @Bean
    public ObterResultadoPautaUseCasePort obterResultadoPautaUseCase(PautaRepositoryPort pautaRepositoryPort,
                                                                   VotoRepositoryPort votoRepositoryPort) {
        return new ObterResultadoPautaUseCase(pautaRepositoryPort, votoRepositoryPort);
    }
}
