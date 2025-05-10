package com.tokseg.armariointeligente.repositories.retirada;

import com.tokseg.armariointeligente.models.retirada.Retirada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetiradaRepository extends JpaRepository<Retirada, Long> {
    List<Retirada> findByRetiradoPor_Id(Long usuarioId);
    List<Retirada> findByEntrega_Id(Long entregaId);
}


