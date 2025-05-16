package com.tokseg.armariointeligente.repositories.compartimento;


import com.tokseg.armariointeligente.models.compartimento.Compartimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompartimentoRepository extends JpaRepository<Compartimento, Long> {
    List<Compartimento> findByArmario_Id(Long armarioId);
    Optional<Compartimento> findByArmario_IdAndCodigo(Long armarioId, String codigo);
}

