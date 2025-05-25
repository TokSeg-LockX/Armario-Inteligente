package com.tokseg.armariointeligente.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitarAberturaDepositoDTO {
    @NotNull(message = "ID do compartimento é obrigatório")
    private Long compartimentoId;

    @NotNull(message = "ID do destinatário é obrigatório")
    private Long destinatarioId;

    @Size(max = 255, message = "Descrição do pacote deve ter no máximo 255 caracteres")
    private String descricaoPacote;
}