package com.tokseg.armariointeligente.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EntregaRequestDTO {
    @NotNull
    private Long compartimentoId;

    @NotBlank
    private String entregador;

    @NotNull
    private Long destinatarioId;

    private String descricao;
}
