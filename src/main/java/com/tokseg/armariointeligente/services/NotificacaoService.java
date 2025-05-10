package com.tokseg.armariointeligente.services;

import com.tokseg.armariointeligente.models.notificacao.Notificacao;
import com.tokseg.armariointeligente.models.notificacao.StatusNotificacao;
import com.tokseg.armariointeligente.repositories.notificacao.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    public Notificacao enviarNotificacao(Notificacao notificacao) {
        // Aqui você pode simular envio (ex: logar no console)
        System.out.println("Enviando notificação: " + notificacao.getConteudo());
        notificacao.setStatus(StatusNotificacao.ENVIADA);
        return notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> listarPorUsuario(Long idUsuario) {
        return notificacaoRepository.findByUsuario_Id(idUsuario);
    }
}
