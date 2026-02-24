package com.sicredi.desafiosicredi.adapter.out.external;

import com.sicredi.desafiosicredi.application.port.out.CpfValidationPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CpfValidationAdapter implements CpfValidationPort {

    private final WebClient webClient;

    public CpfValidationAdapter(WebClient.Builder webClientBuilder, 
                                @Value("${app.cpf-validation.url}") String baseUrl,
                                @Value("${app.cpf-validation.timeout:5000}") int timeout) {
        
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                .responseTimeout(Duration.ofMillis(timeout))
                .doOnConnected(conn -> 
                        conn.addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS)));

        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Override
    @CircuitBreaker(name = "cpfValidation", fallbackMethod = "fallbackIsAbleToVote")
    public boolean isAbleToVote(String cpf) {
        log.info("Validando CPF: {}", cpf);
        
        try {
            CpfResponse response = webClient.get()
                    .uri("/{cpf}", cpf)
                    .retrieve()
                    .onStatus(status -> status.equals(HttpStatus.NOT_FOUND), 
                            clientResponse -> Mono.empty())
                    .bodyToMono(CpfResponse.class)
                    .block();

            if (response == null || "UNABLE_TO_VOTE".equals(response.getStatus())) {
                log.warn("CPF {} inválido ou não autorizado a votar", cpf);
                return false;
            }

            return "ABLE_TO_VOTE".equals(response.getStatus());
        } catch (Exception e) {
            log.error("Erro ao validar CPF {}: {}", cpf, e.getMessage());
            throw e;
        }
    }

    public boolean fallbackIsAbleToVote(String cpf, Throwable t) {
        log.error("Fallback acionado para CPF {}. Erro: {}", cpf, t.getMessage());
        return false;
    }

    @Data
    private static class CpfResponse {
        private String status;
    }
}
