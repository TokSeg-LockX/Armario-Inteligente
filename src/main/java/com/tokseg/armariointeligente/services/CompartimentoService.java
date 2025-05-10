package com.tokseg.armariointeligente.services;

import com.tokseg.armariointeligente.models.compartimento.Compartimento;
import com.tokseg.armariointeligente.models.compartimento.StatusCompartimento;
import com.tokseg.armariointeligente.repositories.compartimento.CompartimentoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompartimentoService {

    private final CompartimentoRepository compartimentoRepository;

    public List<Compartimento> listarPorArmario(Long armarioId) {
        return compartimentoRepository.findByArmario_Id(armarioId);
    }

    public Compartimento atualizarStatus(Long id, StatusCompartimento status) {
        Compartimento c = compartimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compartimento não encontrado."));
        c.setStatus(status);
        return compartimentoRepository.save(c);
    }
}

