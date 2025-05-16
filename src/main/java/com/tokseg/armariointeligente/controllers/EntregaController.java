package com.tokseg.armariointeligente.controllers;

import com.tokseg.armariointeligente.models.entrega.Entrega;
import com.tokseg.armariointeligente.models.entrega.StatusEntrega;
import com.tokseg.armariointeligente.services.EntregaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entregas")
@RequiredArgsConstructor
public class EntregaController {

    private final EntregaService entregaService;

    @PostMapping
    public ResponseEntity<Entrega> registrar(@RequestBody Entrega entrega) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entregaService.registrarEntrega(entrega));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Entrega>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(entregaService.listarPorUsuario(usuarioId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestParam StatusEntrega status) {
        entregaService.atualizarStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
