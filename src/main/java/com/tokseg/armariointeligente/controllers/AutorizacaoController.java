package com.tokseg.armariointeligente.controllers;

import com.tokseg.armariointeligente.models.autorizacao.Autorizacao;
import com.tokseg.armariointeligente.services.AutorizacaoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autorizacoes")
@RequiredArgsConstructor
public class AutorizacaoController {

    private final AutorizacaoService autorizacaoService;

    @PostMapping
    public ResponseEntity<Autorizacao> conceder(@RequestBody Autorizacao autorizacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorizacaoService.conceder(autorizacao));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Autorizacao>> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(autorizacaoService.listarPorUsuario(usuarioId));
    }
}
