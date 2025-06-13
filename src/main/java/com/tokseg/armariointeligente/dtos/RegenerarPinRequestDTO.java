package com.tokseg.armariointeligente.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegenerarPinRequestDTO {
    @NotBlank(message = "O tipo de usuário para regenerar o PIN é obrigatório (ENTREGADOR ou DESTINATARIO)")
    private String tipoUsuarioPin; // "ENTREGADOR" ou "DESTINATARIO"
}