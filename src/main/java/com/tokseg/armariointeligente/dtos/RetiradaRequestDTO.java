package com.tokseg.armariointeligente.dtos;

import com.tokseg.armariointeligente.models.retirada.MetodoAutenticacao;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RetiradaRequestDTO {
    @NotNull
    private Long entregaId;

    @NotNull
    private Long usuarioId;

    @NotNull
    private MetodoAutenticacao metodoAutenticacao;
}
