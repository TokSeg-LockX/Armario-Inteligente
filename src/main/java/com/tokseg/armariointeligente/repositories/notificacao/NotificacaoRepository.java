package com.tokseg.armariointeligente.repositories.notificacao;

import com.tokseg.armariointeligente.models.notificacao.Notificacao;
import com.tokseg.armariointeligente.models.notificacao.StatusNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByUsuario_Id(Long usuarioId);
    List<Notificacao> findByStatus(StatusNotificacao status);
}


