package com.sicredi.desafiosicredi.adapter.in.web;

import com.sicredi.desafiosicredi.adapter.in.web.dto.*;
import com.sicredi.desafiosicredi.application.port.in.*;
import com.sicredi.desafiosicredi.domain.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/pautas")
@RequiredArgsConstructor
@Tag(name = "Pautas", description = "Gerenciamento de pautas, sessões e votação")
public class PautaController {
    private final CriarPautaUseCasePort criarPautaUseCase;
    private final AbrirSessaoUseCasePort abrirSessaoUseCase;
    private final RegistrarVotoUseCasePort registrarVotoUseCase;
    private final ObterResultadoPautaUseCasePort obterResultadoPautaUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria uma nova pauta")
    public PautaResponseDTO criarPauta(@RequestBody @Valid PautaRequestDTO request) {
        Pauta pauta = criarPautaUseCase.execute(new CriarPautaCommand(request.titulo()));
        return new PautaResponseDTO(pauta.getId(), pauta.getTitulo());
    }

    @PostMapping("/{id}/sessao")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Abre uma sessão de votação para uma pauta")
    public SessaoResponseDTO abrirSessao(@PathVariable Long id, @RequestBody(required = false) SessaoRequestDTO request) {
        Integer duracao = (request != null) ? request.duracaoEmMinutos() : null;
        SessaoVotacao sessao = abrirSessaoUseCase.execute(new AbrirSessaoCommand(id, duracao));
        return new SessaoResponseDTO(
                sessao.getId(),
                sessao.getPautaId(),
                sessao.getDataHoraFim(),
                sessao.getStatus().name()
        );
    }

    @PostMapping("/{id}/votos")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registra um voto em uma pauta")
    public VotoResponseDTO registrarVoto(@PathVariable Long id, @RequestBody @Valid VotoRequestDTO request) {
        OpcaoVoto opcao = OpcaoVoto.valueOf(request.voto().toUpperCase());
        Voto voto = registrarVotoUseCase.execute(new RegistrarVotoCommand(id, request.associadoId(), request.cpf(), opcao));
        return new VotoResponseDTO(voto.getPautaId(), voto.getAssociadoId(), voto.getOpcaoVoto().name());
    }

    @GetMapping("/{id}/resultado")
    @Operation(summary = "Obtém o resultado da votação de uma pauta")
    public ResultadoPautaResponseDTO obterResultado(@PathVariable Long id) {
        ResultadoPauta resultado = obterResultadoPautaUseCase.execute(id);
        return new ResultadoPautaResponseDTO(
                resultado.pautaId(),
                resultado.titulo(),
                resultado.votosSim(),
                resultado.votosNao()
        );
    }
}
