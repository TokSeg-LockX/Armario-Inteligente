package com.tokseg.armariointeligente.repositories.entrega;

import com.tokseg.armariointeligente.models.entrega.Entrega;
import com.tokseg.armariointeligente.models.entrega.StatusEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByDestinatario_Id(Long usuarioId);
    List<Entrega> findByStatus(StatusEntrega status);
    // Novo método para encontrar entrega ativa por compartimento para validação de PIN
    // Ajuste os StatusEntrega conforme necessário para definir uma "entrega ativa para PIN"
    Optional<Entrega> findByCompartimento_IdAndStatusIn(Long compartimentoId, List<StatusEntrega> statuses);
}

