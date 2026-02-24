CREATE INDEX idx_voto_pauta_id ON voto(pauta_id);

ALTER TABLE sessao_votacao ADD COLUMN version INTEGER DEFAULT 0 NOT NULL;
