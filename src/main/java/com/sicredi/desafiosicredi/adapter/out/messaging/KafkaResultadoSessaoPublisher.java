package com.sicredi.desafiosicredi.adapter.out.messaging;

import com.sicredi.desafiosicredi.application.port.out.ResultadoSessaoPublisherPort;
import com.sicredi.desafiosicredi.domain.model.SessaoEncerradaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaResultadoSessaoPublisher implements ResultadoSessaoPublisherPort {

    private final KafkaTemplate<String, SessaoEncerradaEvent> kafkaTemplate;

    @Value("${app.kafka.topic-resultado-sessao}")
    private String topic;

    @Override
    public void publish(SessaoEncerradaEvent event) {
        log.info("Publicando resultado da sessao no Kafka. PautaId: {}, Titulo: {}, Votos SIM: {}, Votos NAO: {}",
                event.pautaId(), event.pautaTitulo(), event.votosSim(), event.votosNao());

        try {
            kafkaTemplate.send(topic, String.valueOf(event.pautaId()), event);
        } catch (Exception e) {
            log.error("Erro ao publicar evento de sessao encerrada para a pauta {}: {}", event.pautaId(), e.getMessage(), e);
        }
    }
}
