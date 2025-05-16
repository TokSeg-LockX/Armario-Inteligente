package com.tokseg.armariointeligente.services;

import com.tokseg.armariointeligente.models.compartimento.Compartimento;
import com.tokseg.armariointeligente.models.compartimento.StatusCompartimento;
import com.tokseg.armariointeligente.models.entrega.Entrega;
import com.tokseg.armariointeligente.models.entrega.StatusEntrega;
import com.tokseg.armariointeligente.models.notificacao.Notificacao;
import com.tokseg.armariointeligente.repositories.entrega.EntregaRepository;
import com.tokseg.armariointeligente.repositories.notificacao.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final CompartimentoService compartimentoService;

    public Entrega registrarEntrega(Entrega entrega) {
        Compartimento compartimento = entrega.getCompartimento();
        compartimentoService.atualizarStatus(compartimento.getId(), StatusCompartimento.OCUPADO);
        return entregaRepository.save(entrega);
    }

    public List<Entrega> listarPorUsuario(Long usuarioId) {
        return entregaRepository.findByDestinatario_Id(usuarioId);
    }

    public Entrega buscarPorId(Long id) {
        return entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada."));
    }

    public void atualizarStatus(Long entregaId, StatusEntrega status) {
        Entrega entrega = buscarPorId(entregaId);
        entrega.setStatus(status);
        entregaRepository.save(entrega);
    }
}


