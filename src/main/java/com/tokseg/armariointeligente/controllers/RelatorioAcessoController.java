package com.tokseg.armariointeligente.controllers;

import com.tokseg.armariointeligente.models.relatorioacesso.RelatorioAcesso;
import com.tokseg.armariointeligente.services.RelatorioAcessoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class RelatorioAcessoController {

    private final RelatorioAcessoService relatorioAcessoService;

    @PostMapping
    public ResponseEntity<RelatorioAcesso> registrar(@RequestBody RelatorioAcesso log) {
        return ResponseEntity.status(HttpStatus.CREATED).body(relatorioAcessoService.registrar(log));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RelatorioAcesso>> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(relatorioAcessoService.listarPorUsuario(usuarioId));
    }
}



