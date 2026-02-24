package com.sicredi.desafiosicredi.domain.model;

import java.util.Objects;

public class Pauta {
    private Long id;
    private String titulo;

    public Pauta(Long id, String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título da pauta é obrigatório");
        }
        this.id = id;
        this.titulo = titulo;
    }

    public Pauta(String titulo) {
        this(null, titulo);
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public SessaoVotacao abrirSessao(Integer duracaoEmMinutos) {
        if (this.id == null) {
            throw new IllegalStateException("Pauta deve estar salva para abrir uma sessão");
        }
        return new SessaoVotacao(this.id, duracaoEmMinutos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pauta pauta = (Pauta) o;
        return Objects.equals(id, pauta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
