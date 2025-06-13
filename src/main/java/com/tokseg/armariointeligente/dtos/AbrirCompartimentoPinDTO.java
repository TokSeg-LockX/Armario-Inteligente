package com.tokseg.armariointeligente.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbrirCompartimentoPinDTO {
    @NotNull(message = "ID do compartimento é obrigatório")
    private Long compartimentoId;

    @NotBlank(message = "PIN é obrigatório")
    @Size(min = 6, max = 6, message = "PIN deve ter 6 dígitos")
    @Pattern(regexp = "\\d{6}", message = "PIN deve conter apenas números")
    private String pin;

    @NotBlank(message = "Tipo de usuário da operação é obrigatório (ENTREGADOR ou DESTINATARIO)")
    // Adicionar validação para aceitar apenas "ENTREGADOR" ou "DESTINATARIO", pode ser com enum ou @Pattern
    private String tipoUsuarioOperacao;
}