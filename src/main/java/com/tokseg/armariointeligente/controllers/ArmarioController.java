package com.tokseg.armariointeligente.controllers;

import com.tokseg.armariointeligente.models.armario.Armario;
import com.tokseg.armariointeligente.models.compartimento.Compartimento;
import com.tokseg.armariointeligente.services.ArmarioService;
import com.tokseg.armariointeligente.services.CompartimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/armarios")
@RequiredArgsConstructor
public class ArmarioController {

    private final ArmarioService armarioService;

    @PostMapping
    public ResponseEntity<Armario> criar(@RequestBody Armario armario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(armarioService.cadastrar(armario));
    }

    @GetMapping
    public ResponseEntity<List<Armario>> listar() {
        return ResponseEntity.ok(armarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Armario> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(armarioService.buscarPorId(id));
    }
}
