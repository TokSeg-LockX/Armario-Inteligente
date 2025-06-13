package com.tokseg.armariointeligente.controllers;

import com.tokseg.armariointeligente.dtos.AbrirCompartimentoPinDTO; // Importar
import com.tokseg.armariointeligente.models.compartimento.Compartimento;
import com.tokseg.armariointeligente.models.compartimento.StatusCompartimento;
import com.tokseg.armariointeligente.services.CompartimentoService;
import jakarta.validation.Valid; // Importar
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importar
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compartimentos")
@RequiredArgsConstructor
public class CompartimentoController {

    private final CompartimentoService compartimentoService;

    // Endpoint para abrir compartimento usando PIN
    @PostMapping("/abrir-com-pin")
    @PreAuthorize("isAuthenticated()") // Qualquer usuário autenticado pode tentar, a lógica de serviço valida
    public ResponseEntity<String> abrirCompartimentoComPin(
            @Valid @RequestBody AbrirCompartimentoPinDTO dto) {
        String mensagem = compartimentoService.abrirComPin(
                dto.getCompartimentoId(), dto.getPin(), dto.getTipoUsuarioOperacao()
        );
        return ResponseEntity.ok(mensagem);
    }

    // Endpoints existentes
    @GetMapping("/armario/{armarioId}")
    @PreAuthorize("isAuthenticated()") // Ajustar permissão se necessário
    public ResponseEntity<List<Compartimento>> listarPorArmario(@PathVariable Long armarioId) {
        return ResponseEntity.ok(compartimentoService.listarPorArmario(armarioId));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')") // Apenas Admin deve poder mudar status diretamente
    public ResponseEntity<Compartimento> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusCompartimento status) {
        return ResponseEntity.ok(compartimentoService.atualizarStatus(id, status));
    }
}