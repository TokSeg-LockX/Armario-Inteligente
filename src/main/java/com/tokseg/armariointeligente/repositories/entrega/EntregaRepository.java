package com.tokseg.armariointeligente.repositories.entrega;

import com.tokseg.armariointeligente.models.entrega.Entrega;
import com.tokseg.armariointeligente.models.entrega.StatusEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByDestinatario_Id(Long usuarioId);
    List<Entrega> findByStatus(StatusEntrega status);
}

