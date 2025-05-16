package com.tokseg.armariointeligente.services;

import com.tokseg.armariointeligente.models.autorizacao.Autorizacao;
import com.tokseg.armariointeligente.models.autorizacao.StatusAutorizacao;
import com.tokseg.armariointeligente.repositories.autorizacao.AutorizacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutorizacaoService {

    private final AutorizacaoRepository autorizacaoRepository;

    public Autorizacao conceder(Autorizacao autorizacao) {
        return autorizacaoRepository.save(autorizacao);
    }

    public Optional<Autorizacao> validarAutorizado(Long entregaId, Long autorizadoId) {
        return autorizacaoRepository.findByEntrega_IdAndAutorizado_Id(entregaId, autorizadoId)
                .filter(a -> a.getStatus() == StatusAutorizacao.ATIVA && a.getDataValidade().isAfter(LocalDateTime.now()));
    }

    public List<Autorizacao> listarPorUsuario(Long usuarioId) {
        return autorizacaoRepository.findByUsuario_Id(usuarioId);
    }
}


