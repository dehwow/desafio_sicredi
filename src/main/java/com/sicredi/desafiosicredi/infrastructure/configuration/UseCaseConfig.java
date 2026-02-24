package com.sicredi.desafiosicredi.infrastructure.configuration;

import com.sicredi.desafiosicredi.application.port.in.AbrirSessaoUseCasePort;
import com.sicredi.desafiosicredi.application.port.in.CriarPautaUseCasePort;
import com.sicredi.desafiosicredi.application.port.in.ObterResultadoPautaUseCasePort;
import com.sicredi.desafiosicredi.application.port.in.RegistrarVotoUseCasePort;
import com.sicredi.desafiosicredi.application.usecase.AbrirSessaoUseCase;
import com.sicredi.desafiosicredi.application.usecase.CriarPautaUseCase;
import com.sicredi.desafiosicredi.application.usecase.ObterResultadoPautaUseCase;
import com.sicredi.desafiosicredi.application.usecase.RegistrarVotoUseCase;
import com.sicredi.desafiosicredi.application.usecase.*;
import com.sicredi.desafiosicredi.application.port.out.*;
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
                                                         VotoRepositoryPort votoRepositoryPort,
                                                         CpfValidationPort cpfValidationPort) {
        return new RegistrarVotoUseCase(sessaoVotacaoRepositoryPort, votoRepositoryPort, cpfValidationPort);
    }

    @Bean
    public ObterResultadoPautaUseCasePort obterResultadoPautaUseCase(PautaRepositoryPort pautaRepositoryPort,
                                                                   VotoRepositoryPort votoRepositoryPort,
                                                                   SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort,
                                                                   ResultadoSessaoPublisherPort resultadoSessaoPublisherPort) {
        return new ObterResultadoPautaUseCase(pautaRepositoryPort, votoRepositoryPort, sessaoVotacaoRepositoryPort, resultadoSessaoPublisherPort);
    }

    @Bean
    public EncerrarSessaoUseCase encerrarSessaoUseCase(SessaoVotacaoRepositoryPort sessaoVotacaoRepositoryPort,
                                                       PautaRepositoryPort pautaRepositoryPort,
                                                       VotoRepositoryPort votoRepositoryPort,
                                                       ResultadoSessaoPublisherPort resultadoSessaoPublisherPort) {
        return new EncerrarSessaoUseCase(sessaoVotacaoRepositoryPort, pautaRepositoryPort, votoRepositoryPort, resultadoSessaoPublisherPort);
    }
}
