package com.tokseg.armariointeligente.repositories.autorizacao;
import com.tokseg.armariointeligente.models.autorizacao.Autorizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorizacaoRepository extends JpaRepository<Autorizacao, Long> {
    List<Autorizacao> findByUsuario_Id(Long usuarioId);
    List<Autorizacao> findByAutorizado_Id(Long autorizadoId);
    Optional<Autorizacao> findByEntrega_IdAndAutorizado_Id(Long entregaId, Long autorizadoId);
}

