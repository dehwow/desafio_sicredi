CREATE TABLE pauta (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL
);

CREATE TABLE sessao_votacao (
    id BIGSERIAL PRIMARY KEY,
    pauta_id BIGINT NOT NULL,
    data_hora_inicio TIMESTAMP NOT NULL,
    data_hora_fim TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_sessao_pauta FOREIGN KEY (pauta_id) REFERENCES pauta(id)
);

CREATE TABLE voto (
    id BIGSERIAL PRIMARY KEY,
    associado_id UUID NOT NULL,
    pauta_id BIGINT NOT NULL,
    opcao_voto VARCHAR(10) NOT NULL,
    CONSTRAINT fk_voto_pauta FOREIGN KEY (pauta_id) REFERENCES pauta(id),
    CONSTRAINT uk_voto_associado_pauta UNIQUE (associado_id, pauta_id)
);
