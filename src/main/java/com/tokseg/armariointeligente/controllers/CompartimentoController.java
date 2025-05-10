package com.tokseg.armariointeligente.controllers;

import com.tokseg.armariointeligente.models.compartimento.Compartimento;
import com.tokseg.armariointeligente.models.compartimento.StatusCompartimento;
import com.tokseg.armariointeligente.services.CompartimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compartimentos")
@RequiredArgsConstructor
public class CompartimentoController {

    private final CompartimentoService compartimentoService;

    @GetMapping("/armario/{armarioId}")
    public ResponseEntity<List<Compartimento>> listarPorArmario(@PathVariable Long armarioId) {
        return ResponseEntity.ok(compartimentoService.listarPorArmario(armarioId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Compartimento> atualizarStatus(@PathVariable Long id, @RequestParam StatusCompartimento status) {
        return ResponseEntity.ok(compartimentoService.atualizarStatus(id, status));
    }
}

