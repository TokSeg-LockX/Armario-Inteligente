package com.tokseg.armariointeligente.services;

import com.tokseg.armariointeligente.dtos.RetiradaRequestDTO;
import com.tokseg.armariointeligente.models.compartimento.Compartimento;
import com.tokseg.armariointeligente.models.compartimento.StatusCompartimento;
import com.tokseg.armariointeligente.models.entrega.Entrega;
import com.tokseg.armariointeligente.models.entrega.StatusEntrega;
import com.tokseg.armariointeligente.models.retirada.MetodoAutenticacao;
import com.tokseg.armariointeligente.models.retirada.Retirada;
import com.tokseg.armariointeligente.models.usuario.Usuario;
import com.tokseg.armariointeligente.repositories.entrega.EntregaRepository;
import com.tokseg.armariointeligente.repositories.retirada.RetiradaRepository;
import com.tokseg.armariointeligente.repositories.usuario.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RetiradaService {

    private final RetiradaRepository retiradaRepository;
    private final EntregaService entregaService;
    private final CompartimentoService compartimentoService;

    public Retirada registrarRetirada(Retirada retirada) {
        entregaService.atualizarStatus(retirada.getEntrega().getId(), StatusEntrega.RETIRADO);
        Compartimento compartimento = retirada.getEntrega().getCompartimento();
        compartimentoService.atualizarStatus(compartimento.getId(), StatusCompartimento.DISPONIVEL);
        return retiradaRepository.save(retirada);
    }
}


