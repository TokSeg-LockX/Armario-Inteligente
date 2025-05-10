package com.tokseg.armariointeligente.repositories.relatorioacesso;

import com.tokseg.armariointeligente.models.relatorioacesso.RelatorioAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioAcessoRepository extends JpaRepository<RelatorioAcesso, Long> {
    List<RelatorioAcesso> findByUsuario_Id(Long usuarioId);
    //List<RelatorioAcesso> findByAcao(TipoAcao acao);
}

