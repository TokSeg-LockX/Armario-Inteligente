package com.tokseg.armariointeligente.repositories.armario;

import com.tokseg.armariointeligente.models.armario.Armario;
import com.tokseg.armariointeligente.models.armario.StatusArmario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmarioRepository extends JpaRepository<Armario, Long> {
    List<Armario> findByStatus(StatusArmario status);
}

