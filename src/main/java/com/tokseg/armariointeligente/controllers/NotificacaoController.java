package com.tokseg.armariointeligente.controllers;

import com.tokseg.armariointeligente.models.notificacao.Notificacao;
import com.tokseg.armariointeligente.services.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @PostMapping
    public ResponseEntity<Notificacao> enviar(@RequestBody Notificacao notificacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacaoService.enviarNotificacao(notificacao));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacao>> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacaoService.listarPorUsuario(usuarioId));
    }
}

