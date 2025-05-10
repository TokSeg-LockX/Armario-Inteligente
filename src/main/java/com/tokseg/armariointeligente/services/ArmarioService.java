package com.tokseg.armariointeligente.services;

import com.tokseg.armariointeligente.models.armario.Armario;
import com.tokseg.armariointeligente.repositories.armario.ArmarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArmarioService {

    private final ArmarioRepository armarioRepository;

    public Armario cadastrar(Armario armario) {
        return armarioRepository.save(armario);
    }

    public List<Armario> listarTodos() {
        return armarioRepository.findAll();
    }

    public Armario buscarPorId(Long id) {
        return armarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Armário não encontrado."));
    }
}
