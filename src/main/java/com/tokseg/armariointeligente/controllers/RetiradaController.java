package com.tokseg.armariointeligente.controllers;

import com.tokseg.armariointeligente.models.retirada.Retirada;
import com.tokseg.armariointeligente.services.RetiradaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/retiradas")
@RequiredArgsConstructor
public class RetiradaController {

    private final RetiradaService retiradaService;

    @PostMapping
    public ResponseEntity<Retirada> registrar(@RequestBody Retirada retirada) {
        return ResponseEntity.status(HttpStatus.CREATED).body(retiradaService.registrarRetirada(retirada));
    }
}
